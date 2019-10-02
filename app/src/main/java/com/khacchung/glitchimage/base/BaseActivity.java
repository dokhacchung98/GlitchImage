package com.khacchung.glitchimage.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.khacchung.glitchimage.BuildConfig;
import com.khacchung.glitchimage.activity.HomeActivity;
import com.khacchung.glitchimage.activity.PictureEffectActivity;
import com.khacchung.glitchimage.application.MyApplication;
import com.khacchung.glitchimage.customs.CallBackEffect;
import com.khacchung.glitchimage.customs.CallBackPermission;
import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.customs.RemoveCallBack;
import com.khacchung.glitchimage.util.AdjustBitmap;
import com.khacchung.glitchimage.util.AdsUtil;
import com.khacchung.glitchimage.util.AudienceNetworkInitializeHelper;

import java.io.File;
import java.io.IOException;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements CallBackEffect {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private ProgressDialog dialog;
    public static final String PER_CAMERA = Manifest.permission.CAMERA;
    public static final String PER_READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PER_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PER_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final int CODE_PERMISSION = 12;

    private CallBackPermission callBackPermission;
    public int screenHeight;
    public MyApplication myApplication;

    public com.google.android.gms.ads.InterstitialAd mInterstitialAd;
    public InterstitialAd interstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add device test
        AdSettings.addTestDevice(AdsUtil.HASHED_ID);
        //end add device test

        interstitialAd = new InterstitialAd(this, AdsUtil.INTERSTITIAL_ID);

        mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.id_iterstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdsUtil.HASHED_ID)
                .build());


        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.please_wait));
        dialog.setCancelable(false);
        myApplication = MyApplication.getInstance();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
    }

    public boolean isReadyShowInAds() {
        if (MyApplication.countAction >= 3) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    protected void setMessageDialog(String text) {
        dialog.setMessage(text.isEmpty() ?
                getResources().getString(R.string.please_wait) :
                text);
    }

    protected void setFullScreen() {
        hidenStatusBar();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected void hidenStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

    protected void setTitleToolbar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void showLoading() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    protected void cancleLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
            dialog.dismiss();
        }
    }

    protected void enableBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private int getNavigationBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight)
            return realHeight - usableHeight;
        else
            return 0;
    }

    protected boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void checkPermission(String[] permission,
                                CallBackPermission callBackPermission) {
        this.callBackPermission = callBackPermission;
        boolean check = true;
        for (String per : permission) {
            if (!checkPermission(per)) {
                check = false;
            }
        }

        if (!check) {
            ActivityCompat.requestPermissions(this,
                    permission,
                    CODE_PERMISSION);
        } else {
            callBackPermission.grantedFullPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CODE_PERMISSION && grantResults.length > 0) {
            boolean check = true;
            for (int gr : grantResults) {
                if (gr != PackageManager.PERMISSION_GRANTED) {
                    check = false;
                }
            }

            if (check) {
                if (callBackPermission != null) {
                    callBackPermission.grantedFullPermission();
                }
            } else {
                if (callBackPermission != null) {
                    callBackPermission.notFullPermission();
                }
            }
        }
    }

    protected void showSnackBar(View view, String message) {
        Snackbar snackbar;
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorError));
        TextView textView = snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        snackbar.show();
    }

    @Override
    public void setEffects(int i) {

    }

    public void intentShareImage(String path) {
        MediaScannerConnection.scanFile(this, new String[]{path},

                null, (path1, uri) -> {
                    Intent shareIntent = new Intent(
                            Intent.ACTION_SEND);
                    shareIntent.setType("image/*");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    startActivity(Intent.createChooser(shareIntent,
                            getResources().getString(R.string.share_image)));

                });
    }

    public void intentShareVideo(String path) {
        MediaScannerConnection.scanFile(this, new String[]{path},

                null, (path1, uri) -> {
                    Intent shareIntent = new Intent(
                            Intent.ACTION_SEND);
                    shareIntent.setType("video/*");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    startActivity(Intent.createChooser(shareIntent,
                            getResources().getString(R.string.share_video)));

                });
    }

    protected void intentShareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage = getString(R.string.app_name);
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id="
                    + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.have_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void removeFile(String path, RemoveCallBack removeCallBack) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_alert_save);

        TextView txtAlert = dialog.findViewById(R.id.txt_alert);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnSave.setText(getResources().getString(R.string.remove));
        txtAlert.setText(getResources().getString(R.string.ques_remove));
        btnSave.setOnClickListener(v -> {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE"
                    , Uri.fromFile(file)));
            removeCallBack.removeFileSuccess();
            dialog.cancel();
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                dialog.cancel();
                dialog.dismiss();
            }
            removeCallBack.noRemove();
        });
        Window window = dialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public void gotoGlitchImage(String path) {
        runOnUiThread(() -> {
            myApplication.setPathImage(path);
            try {
                Bitmap bitmap = AdjustBitmap.getCorrectlyOrientedImage(this, Uri.fromFile(new File(path)), screenHeight);
                myApplication.setImgBMP(bitmap);
                MyApplication.imgWidth = bitmap.getWidth();
                MyApplication.imgHeight = bitmap.getHeight();
                Log.e("TAG", "width: " + bitmap.getWidth() + ", height: " + bitmap.getHeight());
                PictureEffectActivity.startIntent(this);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("TAG", "error: " + e.getMessage());
            }
        });
    }
}
