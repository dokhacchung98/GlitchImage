package com.khacchung.glitchimage.activity;

import android.content.Intent;
import android.os.Bundle;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.base.BaseActivity;

public class CameraEffectActivity extends BaseActivity {

    public static void startIntent(BaseActivity activity) {
        activity.startActivity(
                new Intent(activity, CameraEffectActivity.class)
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        hidenStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_effect);

    }
}
