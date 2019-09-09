package com.khacchung.glitchimage.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.github.chrisbanes.photoview.PhotoView;
import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.base.BaseActivity;

public class PreviewActivity extends BaseActivity {
    public static void startIntent(BaseActivity baseActivity, String path, int type) {
        Intent intent = new Intent(baseActivity, PreviewActivity.class);
        intent.putExtra(ListFileActivity.TYPE, type);
        intent.putExtra(ListFileActivity.PATH, path);
        baseActivity.startActivity(intent);
    }

    private int myType;
    private String myPath;
    private PhotoView photoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        enableBackButton();
        setTitleToolbar(getResources().getString(R.string.preview));
        Intent intent = getIntent();
        myPath = intent.getStringExtra(ListFileActivity.PATH);
        myType = intent.getIntExtra(ListFileActivity.TYPE, ListFileActivity.TYPE_IMG);

        photoView = findViewById(R.id.photo_view);
        photoView.setImageURI(Uri.parse(myPath));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.action_share) {
            if (myType == ListFileActivity.TYPE_IMG) {
                intentShareImage(myPath);
            } else if (myType == ListFileActivity.TYPE_VIDEO) {
                intentShareVideo(myPath);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }
}
