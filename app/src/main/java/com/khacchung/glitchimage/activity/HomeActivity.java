package com.khacchung.glitchimage.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.application.MyApplication;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackPermission;
import com.khacchung.glitchimage.util.AdjustBitmap;
import com.khacchung.glitchimage.util.PathManager;

import java.io.IOException;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_PICK_IMAGE = 13;
    private RelativeLayout rlCamera;
    private RelativeLayout rlPhoto;
    private RelativeLayout rlList;
    private int screenHeight;

    private MyApplication myApplication;

    private PathManager pathManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myApplication = MyApplication.getInstance();

        initView();

        pathManager = new PathManager(this);
    }

    private void initView() {
        rlCamera = findViewById(R.id.rl_camera);
        rlPhoto = findViewById(R.id.rl_photo);
        rlList = findViewById(R.id.rl_list);

        rlList.setOnClickListener(this);
        rlPhoto.setOnClickListener(this);
        rlCamera.setOnClickListener(this);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
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
                        BaseActivity.PER_WRITE
                },
                new CallBackPermission() {
                    @Override
                    public void grantedFullPermission() {
                        createFolder();
                        ListFileActivity.startIntent(HomeActivity.this, null, 0);
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
                        getImageFromGallery();
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
                        BaseActivity.PER_WRITE,
                        BaseActivity.PER_CAMERA
                },
                new CallBackPermission() {
                    @Override
                    public void grantedFullPermission() {
                        createFolder();
                        CameraEffectActivity.startIntent(HomeActivity.this);
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

    @SuppressLint("IntentReset")
    private void getImageFromGallery() {
        Intent intent = new Intent(
                "android.intent.action.PICK",
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        getResources().getString(R.string.via_pick_image)
                ),
                REQUEST_CODE_PICK_IMAGE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE_PICK_IMAGE) {
            myApplication.setPathImage(data.getData().toString());
            createFolder();
            try {
                Bitmap bitmap = AdjustBitmap.getCorrectlyOrientedImage(this, data.getData(), screenHeight);
                myApplication.setImgBMP(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PictureEffectActivity.startIntent(HomeActivity.this);
        }
    }

    private void createFolder() {
        if (!pathManager.checkFolderExists(PathManager.FOLDER_IMAGE)) {
            pathManager.createFolderImage();
        }
        if (!pathManager.checkFolderExists(PathManager.FOLDER_VIDEO)) {
            pathManager.createFolderVideo();
        }
    }
}
