package com.khacchung.glitchimage.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.adapter.FolderAdapter;
import com.khacchung.glitchimage.adapter.ImageChooseAdapter;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.models.MyFolder;
import com.khacchung.glitchimage.models.MyImage;

import java.io.File;
import java.util.ArrayList;

public class ChooseImageActivity extends BaseActivity {
    private ArrayList<MyFolder> listFolder = new ArrayList<>();
    private ArrayList<MyImage> listImage = new ArrayList<>();
    private ArrayList<MyImage> listFullImage = new ArrayList<>();
    private FolderAdapter folderAdapter;
    private ImageChooseAdapter imageChooseAdapter;

    private RecyclerView rvFolder;
    private RecyclerView rvImage;

    public static void startIntent(BaseActivity activity) {
        Intent intent = new Intent(activity, ChooseImageActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableBackButton();
        setTitleToolbar(getResources().getString(R.string.choose_image));
        setContentView(R.layout.activity_choose_image);

        rvFolder = findViewById(R.id.rvFolder);
        rvImage = findViewById(R.id.rvImage);

        folderAdapter = new FolderAdapter(this, listFolder);
        imageChooseAdapter = new ImageChooseAdapter(this, listImage);
        rvFolder.setAdapter(folderAdapter);
        rvFolder.setLayoutManager(new GridLayoutManager(this, 1));
        rvImage.setAdapter(imageChooseAdapter);
        rvImage.setLayoutManager(new GridLayoutManager(this, 2));

        getAllFolderContaningImage();
    }

    private void getAllFolderContaningImage() {
        Cursor query = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{"_data", "_id", "bucket_display_name", "bucket_id", "datetaken"},
                null, null, "bucket_display_name DESC");
        assert query != null;
        if (query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("bucket_display_name");
            int columnIndex3 = query.getColumnIndex("datetaken");
            do {
                String imagePath = query.getString(query.getColumnIndex("_data"));
                if (!imagePath.endsWith(".gif")) {
                    query.getString(columnIndex3);
                    String nameFolder = query.getString(columnIndex);
                    boolean check = true;
                    String path = new File(imagePath).getParent() + "/";
                    for (MyFolder t : listFolder) {
                        if (t.getPathFolder().trim().equals(path.trim())) {
                            check = false;
                            break;
                        }
                    }

                    listFullImage.add(new MyImage(imagePath, path));
                    if (check) {
                        File file[] = new File(path).listFiles();
                        int t = 0;
                        for (File f : file) {
                            if (f.getName().endsWith(".png")
                                    || f.getName().endsWith(".jpg")
                                    || f.getName().endsWith(".jpeg")) {
                                t++;
                            }
                        }
                        listFolder.add(new MyFolder(path, nameFolder, t));
                    }
                }
            } while (query.moveToNext());
        }

        if (listFolder.size() > 0) {
            listFolder.get(0).setSelect(true);
            getAllImageFromPathFolder(listFolder.get(0).getPathFolder());
        }
        folderAdapter.notifyDataSetChanged();
    }

    public void getAllImageFromPathFolder(String pathFolder) {
        listImage.clear();
        for (MyImage tmp : listFullImage) {
            if (tmp.getPathParent().trim().equals(pathFolder.trim())) {
                listImage.add(tmp);
            }
        }
        imageChooseAdapter.notifyDataSetChanged();
    }

    public void glitchImage(String pathImage) {
        Log.e("TAG", "pathImage" + pathImage);
        gotoGlitchImage(pathImage);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
