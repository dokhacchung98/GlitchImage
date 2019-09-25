package com.khacchung.glitchimage.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    private static final int MIN_PREVIEW_WIDTH = 1280;
    private static final int MIN_PREVIEW_HEIGHT = 720;

    private static final int MAX_PREVIEW_WIDTH = 1920;
    private static final int MAX_PREVIEW_HEIGHT = 1080;
    private RenderPipeline renderPipeline = new RenderPipeline();
    private LinearLayout lnHand;
    private RelativeLayout rnHand;
    private Animation animation1;
    private Animation animation2;

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

    private CameraManager manager;

    private RelativeLayout relativeEffect;
    private RecyclerView recyclerView;
    private SurfaceFitView surfaceFitView;
    private ImageButton btnBack;
    private ImageButton btnSwitchCam;
    private CircularImageView imgListPhoto;
    private ImageButton btnSwitchMode;
    private ImageButton btnTake;
    private TextView txtTime;
    private RelativeLayout lnHint;

    private int screenHeight;
    private int screenWidth;
    private boolean isFontCamera = false;
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
    private long timeInMilliseconds = 0;
    private long timeSwapBuff = 0;
    private long updatedTime = 0;
    private String mCameraId;

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
        currentFilter = new FilterRender();
        nameFile = "video" + System.currentTimeMillis() + ".mp4";
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
        lnHand = findViewById(R.id.ln_hand);
        rnHand = findViewById(R.id.rl_move_hand);

        btnBack = findViewById(R.id.btn_back);
        btnSwitchCam = findViewById(R.id.btn_switch_cam);
        imgListPhoto = findViewById(R.id.img_list);
        btnSwitchMode = findViewById(R.id.btn_switch_mode);
        btnTake = findViewById(R.id.btn_take);

        btnBack.setOnClickListener(this);
        btnSwitchCam.setOnClickListener(this);
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

        MyApplication.imgHeight = screenHeight;
        MyApplication.imgWidth = screenWidth;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relativeEffect.getLayoutParams();
        params.width = (screenWidth);
        params.height = (screenHeight * 229) / 1920;
        params.addRule(13);
        relativeEffect.setLayoutParams(params);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.anim_hand_1);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.anim_hand_2);
        lnHand.setAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lnHand.setAnimation(animation2);
                animation2.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lnHand.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rnHand.setVisibility(View.GONE);
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
                        BaseActivity.PER_WRITE,
                        BaseActivity.PER_AUDIO
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

        btnTake.setImageResource(isTakePhoto ?
                R.drawable.background_button_take_photo : R.drawable.background_button_record);
        btnSwitchMode.setImageResource(isTakePhoto ? R.drawable.ic_video : R.drawable.ic_camera);
    }

    private void startTakeOrRecord() {
        if (isTakePhoto) {
            saveImage();
        } else {
            if (!isRecord) {
                startRecording();
            } else {
                stopRecordingAndViewer();
            }
        }
    }

    private void gotoListFileCreatedActivity() {
        ListFileActivity.startIntent(this, null, 0);
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

    private void checkPositionToStartAnimation(int pos) {
        boolean check = false;
        for (int item : GalleryEffect.listPositionNone) {
            if (item == pos) {
                check = true;
            }
        }
        if (!check) {
            rnHand.setVisibility(View.VISIBLE);
            animation1.reset();
            animation2.reset();
            lnHand.setVisibility(View.VISIBLE);
            lnHand.setAnimation(animation1);
            animation1.start();
        } else {
            rnHand.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEffects(int i) {
        checkPositionToStartAnimation(i);
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

        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics cameraCharacteristics =
                    null;
            if (manager != null) {
                mCameraId = manager.getCameraIdList()[0];
                cameraCharacteristics = manager.getCameraCharacteristics(isFontCamera ? "1" : "0");
            }
            StreamConfigurationMap map =
                    cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (map != null && checkPermission(BaseActivity.PER_CAMERA)) {
                Size[] sizes = map.getOutputSizes(android.graphics.ImageFormat.JPEG);
                Size largest = Collections.max(Arrays.asList(sizes), new CompareSizesByArea());
                int mSensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                int displayRotation = getWindowManager().getDefaultDisplay().getRotation();
                boolean swappedDimensions = false;
                switch (displayRotation) {
                    case Surface.ROTATION_0:
                    case Surface.ROTATION_180:
                        if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                            swappedDimensions = true;
                        }
                        break;
                    case Surface.ROTATION_90:
                    case Surface.ROTATION_270:
                        if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                            swappedDimensions = true;
                        }
                        break;
                }


                Point displaySize = new Point();
                getWindowManager().getDefaultDisplay().getSize(displaySize);
                int rotatedPreviewWidth = MIN_PREVIEW_WIDTH;
                int rotatedPreviewHeight = MIN_PREVIEW_HEIGHT;
                int maxPreviewWidth = displaySize.x;
                int maxPreviewHeight = displaySize.y;
                if (swappedDimensions) {
                    rotatedPreviewWidth = MIN_PREVIEW_HEIGHT;
                    rotatedPreviewHeight = MIN_PREVIEW_WIDTH;
                    maxPreviewWidth = displaySize.y;
                    maxPreviewHeight = displaySize.x;
                }

                if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                    maxPreviewWidth = MAX_PREVIEW_WIDTH;
                }
                if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                    maxPreviewHeight = MAX_PREVIEW_HEIGHT;
                }
                previewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                        rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                        maxPreviewHeight, largest);
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
            this.recyclerView.setVisibility(View.GONE);
            this.btnSwitchMode.setVisibility(View.GONE);
            this.txtTime.setVisibility(View.VISIBLE);
            this.customHandler.postDelayed(this.updateTimerThread, 0);
            this.iSupportRecord.startRecording();
            this.iSupportRecord.enableRecordAudio(true);

        }
    }

    public void stopRecordingAndViewer() {
        stopRecord();
        gotoViewer();

    }

    private void gotoViewer() {
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE",
                Uri.fromFile(new File(pathVideo))));
        ListFileActivity.startIntent(CameraEffectActivity.this, pathVideo, ListFileActivity.TYPE_VIDEO);
        finish();
    }

    private void stopRecord() {
        this.isRecord = false;
        this.timeSwapBuff += this.timeInMilliseconds;
        this.customHandler.removeCallbacks(this.updateTimerThread);
        this.txtTime.setText(getResources().getString(R.string.start_time));
        this.startTime = 0;
        if (this.iSupportRecord != null) {
            this.iSupportRecord.stopRecording();
            this.lnHint.setVisibility(View.VISIBLE);
            this.imgListPhoto.setVisibility(View.VISIBLE);
            this.recyclerView.setVisibility(View.VISIBLE);
            this.btnSwitchMode.setVisibility(View.VISIBLE);
            this.txtTime.setVisibility(View.GONE);
        }
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

    private Size chooseOptimalSize(Size[] choices, int textureViewWidth, int textureViewHeight,
                                   int maxWidth, int maxHeight, Size aspectRatio) {
        List<Size> bigEnough = new ArrayList<>();
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                    option.getHeight() == option.getWidth() * h / w) {
                if (option.getWidth() >= textureViewWidth &&
                        option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            return choices[0];
        }
    }

    class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    @Override
    protected void onDestroy() {
        releaseCamera();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isRecord) {
            showDialogAlertSaveVideo();
        } else
            super.onBackPressed();
    }

    private void showDialogAlertSaveVideo() {
        stopRecord();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_alert_save);

        TextView txtAlert = dialog.findViewById(R.id.txt_alert);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        txtAlert.setText(getResources().getString(R.string.ques_save2));
        btnSave.setOnClickListener(v -> gotoViewer());
        btnCancel.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                dialog.cancel();
                dialog.dismiss();
                super.onBackPressed();
            }
            File f = new File(pathVideo);
            if (f.exists()) {
                f.delete();
            }
        });
        Window window = dialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}
