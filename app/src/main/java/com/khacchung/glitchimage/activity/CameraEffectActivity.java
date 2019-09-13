package com.khacchung.glitchimage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.adapter.EffectAdapter;
import com.khacchung.glitchimage.application.MyApplication;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackPermission;
import com.khacchung.glitchimage.util.GalleryEffect;
import com.khacchung.glitchimage.util.PathManager;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import cn.ezandroid.ezfilter.EZFilter;
import cn.ezandroid.ezfilter.camera.ISupportTakePhoto;
import cn.ezandroid.ezfilter.core.FBORender;
import cn.ezandroid.ezfilter.core.FilterRender;
import cn.ezandroid.ezfilter.core.GLRender;
import cn.ezandroid.ezfilter.core.RenderPipeline;
import cn.ezandroid.ezfilter.core.environment.SurfaceFitView;
import cn.ezandroid.ezfilter.media.record.ISupportRecord;
import cn.ezandroid.ezfilter.media.record.RecordableRender;

public class CameraEffectActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private RenderPipeline renderPipeline = new RenderPipeline();

    private PathManager pathManager;
    private int pos = 0;
    private List<String> effectName;
    private int[] icon = {
            R.drawable.ic_0,
            R.drawable.ic_6,
            R.drawable.ic_7,
            R.drawable.ic_14,
            R.drawable.ic_15,
            R.drawable.ic_16,
            R.drawable.ic_18,
            R.drawable.ic_20,
            R.drawable.ic_26,
            R.drawable.ic_12,
            R.drawable.ic_30,
            R.drawable.ic_31,
            R.drawable.ic_46,
            R.drawable.ic_31,
            R.drawable.ic_32,
            R.drawable.ic_33,
            R.drawable.ic_34,
            R.drawable.ic_35,
            R.drawable.ic_36,
            R.drawable.ic_1,
            R.drawable.ic_2,
            R.drawable.ic_3,
            R.drawable.ic_4,
            R.drawable.ic_5,
            R.drawable.ic_8,
            R.drawable.ic_9,
            R.drawable.ic_10,
            R.drawable.ic_11,
            R.drawable.ic_17,
            R.drawable.ic_19,
            R.drawable.ic_21,
            R.drawable.ic_22,
            R.drawable.ic_23,
            R.drawable.ic_24,
            R.drawable.ic_25,
            R.drawable.ic_27,
            R.drawable.ic_28,
            R.drawable.ic_29,
            R.drawable.ic_37,
            R.drawable.ic_38,
            R.drawable.ic_40,
            R.drawable.ic_41,
            R.drawable.ic_43,
            R.drawable.ic_42,
            R.drawable.ic_13,
            R.drawable.ic_45
    };
    private EffectAdapter effectAdapter;

    private RelativeLayout relativeEffect;
    private RecyclerView recyclerView;
    private SurfaceFitView surfaceFitView;
    private ImageButton btnBack;
    private ImageButton btnSwitchCam;
    private ImageButton btnSwitchFlash;
    private ImageButton btnSwitchVoice;
    private CircularImageView imgListPhoto;
    private ImageButton btnSwitchMode;
    private ImageButton btnTake;
    private TextView txtTime;
    private RelativeLayout lnHint;

    private int screenHeight;
    private int screenWidth;
    private boolean isFontCamera = false;
    private boolean isVoice = true;
    private boolean isFlash = true;
    private boolean isTakePhoto = true;

    private FilterRender currentFilter;
    private CameraDevice cameraDevice;
    private Size previewSize;
    private ISupportRecord iSupportRecord;
    private ISupportTakePhoto iSupportTakePhoto;

    private String pathVideo;
    private String nameFile;

    private boolean isRecord = false;
    private long startTime = 0;
    private boolean take_photo = true;
    private long timeInMilliseconds = 0;
    private long timeSwapBuff = 0;
    private long updatedTime = 0;

    private Handler customHandler = new Handler();

    public static void startIntent(BaseActivity activity) {
        activity.startActivity(
                new Intent(activity, CameraEffectActivity.class)
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        hidenStatusBar();
        setFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_effect);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int w = displayMetrics.widthPixels;
        int h = (w * 3) / 4;
//        int h = displayMetrics.heightPixels;
        previewSize = new Size(w, h);
        currentFilter = new FilterRender();
        nameFile = "video" + System.currentTimeMillis() + ".mp4";

        MyApplication.imgHeight = h;
        MyApplication.imgWidth = w;
        Log.e("taGa", "w" + w + ", h:" + h);
        initView();
        openCamera();
    }

    private void initView() {
        recyclerView = findViewById(R.id.rcv_filters);
        relativeEffect = findViewById(R.id.relative_effects);
        surfaceFitView = findViewById(R.id.render_view);
        surfaceFitView.setOnTouchListener(this);
        surfaceFitView.setRenderMode(1);
        txtTime = findViewById(R.id.txt_time);
        lnHint = findViewById(R.id.rl_function);

        btnBack = findViewById(R.id.btn_back);
        btnSwitchCam = findViewById(R.id.btn_switch_cam);
        btnSwitchFlash = findViewById(R.id.btn_switch_flash);
        btnSwitchVoice = findViewById(R.id.btn_switch_mic);
        imgListPhoto = findViewById(R.id.img_list);
        btnSwitchMode = findViewById(R.id.btn_switch_mode);
        btnTake = findViewById(R.id.btn_take);

        btnBack.setOnClickListener(this);
        btnSwitchCam.setOnClickListener(this);
        btnSwitchFlash.setOnClickListener(this);
        btnSwitchVoice.setOnClickListener(this);
        imgListPhoto.setOnClickListener(this);
        btnSwitchMode.setOnClickListener(this);
        btnTake.setOnClickListener(this);

        effectName = GalleryEffect.getName();
        effectAdapter = new EffectAdapter(this, effectName, icon);
        recyclerView.setAdapter(effectAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        );
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relativeEffect.getLayoutParams();
        params.width = (screenWidth);
        params.height = (screenHeight * 229) / 1920;
        params.addRule(13);
        relativeEffect.setLayoutParams(params);
    }

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camDevice) {
            cameraDevice = camDevice;
            renderPipeline = EZFilter.input(cameraDevice, previewSize)
                    .addFilter(currentFilter)
                    .enableRecord(pathVideo, true, true)
                    .into(surfaceFitView);
            FBORender startPointRender = renderPipeline.getStartPointRender();
            if (startPointRender instanceof ISupportTakePhoto) {
                iSupportTakePhoto = (ISupportTakePhoto) startPointRender;
            }
            for (GLRender glRender : renderPipeline.getEndPointRenders()) {
                if (glRender instanceof RecordableRender) {
                    iSupportRecord = (ISupportRecord) glRender;
                }
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            Toast.makeText(CameraEffectActivity.this,
                    getResources().getString(R.string.have_error),
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_switch_cam:
                switchCamera();
                break;
            case R.id.btn_switch_flash:
                switchFlashMode();
                break;
            case R.id.btn_switch_mic:
                switchVoiceMode();
                break;
            case R.id.img_list:
                gotoListFileCreatedActivity();
                break;
            case R.id.btn_take:
                startTakeOrRecord();
                break;
            case R.id.btn_switch_mode:
                switchModeCamera();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission(new String[]{
                        BaseActivity.PER_CAMERA,
                        BaseActivity.PER_READ,
                        BaseActivity.PER_WRITE
                },
                new CallBackPermission() {
                    @Override
                    public void grantedFullPermission() {
                        if (pathManager == null) {
                            pathManager = new PathManager(CameraEffectActivity.this);
                        }
                        if (!pathManager.checkFolderExists(PathManager.FOLDER_VIDEO)) {
                            pathManager.createFolderVideo();
                        }
                        getVideoFilePath();
                    }

                    @Override
                    public void notFullPermission() {
                        Toast.makeText(CameraEffectActivity.this,
                                getResources().getString(R.string.error_permission),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void switchModeCamera() {
        isTakePhoto = !isTakePhoto;

        btnTake.setImageResource(isTakePhoto ? R.drawable.background_button_take_photo : R.drawable.background_button_record);
        btnSwitchVoice.setVisibility(isTakePhoto ? View.GONE : View.VISIBLE);
        btnSwitchFlash.setVisibility(isTakePhoto ? View.VISIBLE : View.GONE);
        btnSwitchMode.setImageResource(isTakePhoto ? R.drawable.ic_video : R.drawable.ic_camera);
    }

    private void startTakeOrRecord() {
        if (isTakePhoto) {
            saveImage();
        } else {
            if (!isRecord) {
                startRecording();
            } else {
                stopRecording();
            }
        }
    }

    private void gotoListFileCreatedActivity() {
        ListFileActivity.startIntent(this, null, 0);
    }

    private void switchVoiceMode() {
        isVoice = !isVoice;
        btnSwitchVoice.setImageResource(isVoice ? R.drawable.ic_voice : R.drawable.ic_voice_mute);
    }

    private void switchFlashMode() {
        isFlash = !isFlash;
        btnSwitchFlash.setImageResource(isFlash ? R.drawable.ic_flash_on : R.drawable.ic_flash_off);
    }

    private void switchCamera() {
        isFontCamera = !isFontCamera;
        releaseCamera();
        openCamera();
    }

    private void saveImage() {
        if (pathManager.checkFolderExists(PathManager.FOLDER_IMAGE)) {
            pathManager.createFolderImage();
        }
        StringBuilder pathFile = new StringBuilder();
        pathFile.append(PathManager.getPathFolder(this));
        pathFile.append(PathManager.FOLDER_IMAGE);
        pathFile.append("/img");
        pathFile.append(System.currentTimeMillis());
        pathFile.append(".jpg");

        renderPipeline.output(bitmap ->
                runOnUiThread(() ->
                        saveBitmap(bitmap, pathFile.toString()))
        );
    }

    public void saveBitmap(Bitmap bitmap, String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE",
                    Uri.fromFile(new File(file.getAbsolutePath()))));

            Toast.makeText(CameraEffectActivity.this,
                    getResources().getString(R.string.save) + str,
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListFileActivity.startIntent(this, str, ListFileActivity.TYPE_IMG);
        finish();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                GalleryEffect.setTouch(pos, x, y);
        }
        return true;
    }

    @Override
    public void setEffects(int i) {
        releaseCamera();
        openCamera();
        if (iSupportRecord != null) {
            iSupportRecord.enableRecordAudio(true);
        }
        this.pos = i;
        renderPipeline.removeFilterRender(currentFilter);
        renderPipeline.clearEndPointRenders();

        this.currentFilter = GalleryEffect.getEffect(this, i);

        renderPipeline = EZFilter.input(cameraDevice, previewSize)
                .addFilter(currentFilter)
                .enableRecord(pathVideo, true, true)
                .into(surfaceFitView);
        FBORender startPointRender = renderPipeline.getStartPointRender();
        if (startPointRender instanceof ISupportTakePhoto) {
            iSupportTakePhoto = (ISupportTakePhoto) startPointRender;
        }
        for (GLRender glRender : renderPipeline.getEndPointRenders()) {
            if (glRender instanceof RecordableRender) {
                iSupportRecord = (ISupportRecord) glRender;
            }
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics cameraCharacteristics =
                    null;
            if (manager != null) {
                cameraCharacteristics = manager.getCameraCharacteristics(isFontCamera ? "1" : "0");
            }
            StreamConfigurationMap map =
                    cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (map != null && checkPermission(BaseActivity.PER_CAMERA)) {
                if (manager != null) {
                    manager.openCamera(isFontCamera ? "1" : "0", stateCallback, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releaseCamera() {
        if (cameraDevice != null) {
            cameraDevice.close();
        }
        cameraDevice = null;
    }

    private void startRecording() {
        this.isRecord = true;
        if (this.iSupportRecord != null) {
            this.startTime = SystemClock.uptimeMillis();
            this.lnHint.setVisibility(View.GONE);
            this.imgListPhoto.setVisibility(View.GONE);
            this.relativeEffect.setVisibility(View.GONE);
            this.btnSwitchMode.setVisibility(View.GONE);
            this.txtTime.setVisibility(View.VISIBLE);
            this.customHandler.postDelayed(this.updateTimerThread, 0);
            this.iSupportRecord.startRecording();
        }
    }

    public void stopRecording() {
        this.isRecord = false;
        this.timeSwapBuff += this.timeInMilliseconds;
        this.customHandler.removeCallbacks(this.updateTimerThread);
        this.txtTime.setText(getResources().getString(R.string.start_time));
        if (this.iSupportRecord != null) {
            this.iSupportRecord.stopRecording();
            this.lnHint.setVisibility(View.VISIBLE);
            this.imgListPhoto.setVisibility(View.VISIBLE);
            this.relativeEffect.setVisibility(View.VISIBLE);
            this.btnSwitchMode.setVisibility(View.VISIBLE);
            this.txtTime.setVisibility(View.GONE);
        }
        ListFileActivity.startIntent(this, pathVideo, ListFileActivity.TYPE_VIDEO);
        this.nameFile = System.currentTimeMillis() + ".mp4";
        getVideoFilePath();
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int i = (int) (updatedTime / 1000);
            int i2 = i / 60;
            int i3 = i % 60;
            long j = updatedTime % 1000;
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(String.format("%02d", new Object[]{Integer.valueOf(i2)}));
            sb.append(":");
            sb.append(String.format("%02d", new Object[]{Integer.valueOf(i3)}));
            txtTime.setText(sb.toString());
            customHandler.postDelayed(this, 0);
        }
    };

    private void getVideoFilePath() {
        File file = new File(PathManager.getPathFolder(this)
                + PathManager.FOLDER_VIDEO,
                nameFile);
        if (file.exists()) {
            file.delete();
        }
        pathVideo = file.getAbsolutePath();
    }
}
