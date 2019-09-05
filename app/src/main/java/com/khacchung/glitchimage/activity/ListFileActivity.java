package com.khacchung.glitchimage.activity;

import android.content.Intent;
import android.os.Bundle;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.base.BaseActivity;

public class ListFileActivity extends BaseActivity {
    public static final String TYPE = "TYPE";
    public static final String PATH = "PATH";
    public static final int TYPE_IMG = 1;
    public static final int TYPE_VIDEO = 2;

    public static void startIntent(BaseActivity activity, String path, int type) {
        Intent intent = new Intent(activity, ListFileActivity.class);
        intent.putExtra(PATH, path);
        intent.putExtra(TYPE, type);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        hidenStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_file);
        enableBackButton();
        Intent intent = getIntent();
        String path = intent.getStringExtra(PATH);
        int type = intent.getIntExtra(TYPE, 0);
        if (path != null && (type == TYPE_IMG || type == TYPE_VIDEO)) {
            PreviewActivity.startIntent(this, path, type);
        }
    }
}
