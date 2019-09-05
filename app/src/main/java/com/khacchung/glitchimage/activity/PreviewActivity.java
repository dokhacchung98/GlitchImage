package com.khacchung.glitchimage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        enableBackButton();
        Intent intent = getIntent();
        myPath = intent.getStringExtra(ListFileActivity.PATH);
        myType = intent.getIntExtra(ListFileActivity.TYPE, ListFileActivity.TYPE_IMG);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
