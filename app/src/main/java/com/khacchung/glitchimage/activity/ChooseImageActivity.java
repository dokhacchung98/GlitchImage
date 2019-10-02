package com.khacchung.glitchimage.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.adapter.FolderAdapter;
import com.khacchung.glitchimage.adapter.ImageChooseAdapter;
import com.khacchung.glitchimage.application.MyApplication;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.models.MyFolder;
import com.khacchung.glitchimage.models.MyImage;
import com.khacchung.glitchimage.util.AdsUtil;
import com.khacchung.glitchimage.util.AudienceNetworkInitializeHelper;

import java.io.File;
import java.util.ArrayList;

public class ChooseImageActivity extends BaseActivity {
    private static final String TAG = ChooseImageActivity.class.getSimpleName();
    private ArrayList<MyFolder> listFolder = new ArrayList<>();
    private ArrayList<MyImage> listImage = new ArrayList<>();
    private ArrayList<MyImage> listFullImage = new ArrayList<>();
    private FolderAdapter folderAdapter;
    private ImageChooseAdapter imageChooseAdapter;

    private RecyclerView rvFolder;
    private RecyclerView rvImage;
    private AdView adView;
    private com.google.android.gms.ads.AdView mAdView;
    private AdRequest adRequest;

    public static void startIntent(BaseActivity activity) {
        Intent intent = new Intent(activity, ChooseImageActivity.class);
        activity.startActivity(intent);
        MyApplication.addCountAction();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableBackButton();
        setTitleToolbar(getResources().getString(R.string.choose_image));
        setContentView(R.layout.activity_choose_image);
        //ads
        AudienceNetworkInitializeHelper.initialize(this);

        rvFolder = findViewById(R.id.rvFolder);
        rvImage = findViewById(R.id.rvImage);

        //ads
        adView = new AdView(this, AdsUtil.BANNER_ID, AdSize.BANNER_HEIGHT_50);
        mAdView = findViewById(R.id.adView);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(adView);
        // Request an ad
        adView.loadAd();
        adView.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
                Log.e(TAG, "loadAdsFacebook onError()");
                adRequest = new AdRequest.Builder()
                        .addTestDevice(AdsUtil.HASHED_ID)
                        .build();
                mAdView.loadAd(adRequest);
            }
        });

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

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
