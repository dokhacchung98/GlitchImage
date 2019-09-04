package com.khacchung.glitchimage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackPermission;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlCamera;
    private RelativeLayout rlPhoto;
    private RelativeLayout rlList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView() {
        rlCamera = findViewById(R.id.rl_camera);
        rlPhoto = findViewById(R.id.rl_photo);
        rlList = findViewById(R.id.rl_list);

        rlList.setOnClickListener(this);
        rlPhoto.setOnClickListener(this);
        rlCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_camera:
                gotoCameraActivity();
                break;
            case R.id.rl_photo:
                gotoChosePhoto();
                break;
            case R.id.rl_list:
                gotoListFileCreated();
                break;
        }
    }

    private void gotoListFileCreated() {
        checkPermission(new String[]{
                        BaseActivity.PER_READ,
                        BaseActivity.PER_WRITE,
                        BaseActivity.PER_CAMERA
                },
                new CallBackPermission() {
                    @Override
                    public void grantedFullPermission() {

                    }

                    @Override
                    public void notFullPermission() {
                        errorRequestPermission();
                    }
                });
    }

    private void gotoChosePhoto() {
        checkPermission(new String[]{
                        BaseActivity.PER_READ,
                        BaseActivity.PER_WRITE
                },
                new CallBackPermission() {
                    @Override
                    public void grantedFullPermission() {

                    }

                    @Override
                    public void notFullPermission() {
                        errorRequestPermission();
                    }
                });
    }

    private void gotoCameraActivity() {
        checkPermission(new String[]{
                        BaseActivity.PER_READ,
                        BaseActivity.PER_WRITE
                },
                new CallBackPermission() {
                    @Override
                    public void grantedFullPermission() {

                    }

                    @Override
                    public void notFullPermission() {
                        errorRequestPermission();
                    }
                });
    }

    private void errorRequestPermission() {
        showSnackBar(rlCamera, getString(R.string.error_permission));
    }
}
