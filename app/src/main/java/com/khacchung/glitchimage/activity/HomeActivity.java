package com.khacchung.glitchimage.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.application.MyApplication;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackPermission;
import com.khacchung.glitchimage.util.AdsUtil;
import com.khacchung.glitchimage.util.AudienceNetworkInitializeHelper;
import com.khacchung.glitchimage.util.PathManager;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private RelativeLayout rlCamera;
    private RelativeLayout rlPhoto;
    private RelativeLayout rlList;
    private ImageButton btnExit;
    private LinearLayout btnShare;
    private LinearLayout btnMore;
    private LinearLayout btnRate;
    private ProgressBar progressBar;

    private LinearLayout lnMain;
    private LinearLayout lnBot;
    private ImageView imgLogo;
    private AdView adView;
    private com.google.android.gms.ads.AdView mAdView;
    private AdRequest adRequest;

    private PathManager pathManager;

    private Animation animation;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myApplication = MyApplication.getInstance();

        //ads
        AudienceNetworkInitializeHelper.initialize(this);

        initView();

        handler = new Handler();
        handler.postDelayed(() -> {
            interstitialAd.loadAd();
            interstitialAd.setAdListener(new AbstractAdListener() {
                @Override
                public void onInterstitialDismissed(Ad ad) {
                    super.onInterstitialDismissed(ad);
                    Log.e(TAG, "onInterstitialDismissed()");
                    startAnim();
                }

                @Override
                public void onError(Ad ad, AdError error) {
                    super.onError(ad, error);
                    Log.e(TAG, "load Ads Facebook onError()");
                    mInterstitialAd.setAdListener(new AdListener(){
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            startAnim();
                        }

                        @Override
                        public void onAdFailedToLoad(int i) {
                            super.onAdFailedToLoad(i);
                            Log.e(TAG, "load Admob onError()");
                            startAnim();
                        }
                    });
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                       startAnim();
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    super.onAdLoaded(ad);
                    Log.e(TAG, "onAdLoaded()");
                    interstitialAd.show();
                }
            });
        }, 2000);

        pathManager = new PathManager(this);
    }

    private void initView() {
        rlCamera = findViewById(R.id.rl_camera);
        rlPhoto = findViewById(R.id.rl_photo);
        rlList = findViewById(R.id.rl_list);
        btnExit = findViewById(R.id.btn_exit);
        btnMore = findViewById(R.id.ln_more);
        btnShare = findViewById(R.id.ln_share);
        btnRate = findViewById(R.id.ln_rate);

        lnMain = findViewById(R.id.ln_main);
        lnBot = findViewById(R.id.ln_bot);
        imgLogo = findViewById(R.id.img_logo);
        progressBar = findViewById(R.id.process_loading);
        mAdView = findViewById(R.id.adView);

        lnBot.setVisibility(View.GONE);
        lnMain.setVisibility(View.GONE);
        btnExit.setVisibility(View.GONE);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_logo_start);
        imgLogo.setAnimation(animation);
        animation.start();
    }

    private void startAnim() {
        progressBar.setVisibility(View.GONE);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_logo);
        imgLogo.setAnimation(animation);
        animation.start();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lnBot.setVisibility(View.VISIBLE);
                lnMain.setVisibility(View.VISIBLE);
                btnExit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rlList.setOnClickListener(this);
        rlPhoto.setOnClickListener(this);
        rlCamera.setOnClickListener(this);
        btnRate.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnMore.setOnClickListener(this);

        //ads
        adView = new AdView(this, AdsUtil.BANNER_ID, AdSize.BANNER_HEIGHT_50);
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_camera:
                if (isReadyShowInAds()) {
                    interstitialAd.loadAd();
                    interstitialAd.setAdListener(new AbstractAdListener() {
                        @Override
                        public void onError(Ad ad, AdError error) {
                            super.onError(ad, error);
                            Log.e(TAG, "load Ads Facebook onError()");
                            mInterstitialAd.setAdListener(new AdListener(){
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    gotoCameraActivity();
                                }

                                @Override
                                public void onAdFailedToLoad(int i) {
                                    super.onAdFailedToLoad(i);
                                    Log.e(TAG, "load Admob onError()");
                                    gotoCameraActivity();
                                }
                            });
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                gotoCameraActivity();
                            }
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            super.onAdLoaded(ad);
                            interstitialAd.show();
                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            super.onInterstitialDismissed(ad);
                            gotoCameraActivity();
                        }
                    });
                } else {
                    gotoCameraActivity();
                }
                break;
            case R.id.rl_photo:
                if (isReadyShowInAds()) {
                    interstitialAd.loadAd();
                    interstitialAd.setAdListener(new AbstractAdListener() {
                        @Override
                        public void onError(Ad ad, AdError error) {
                            super.onError(ad, error);
                            Log.e(TAG, "load Ads Facebook onError()");
                            mInterstitialAd.setAdListener(new AdListener(){
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    gotoChosePhoto();
                                }

                                @Override
                                public void onAdFailedToLoad(int i) {
                                    super.onAdFailedToLoad(i);
                                    Log.e(TAG, "load Admob onError()");
                                    gotoChosePhoto();
                                }
                            });
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                Log.e(TAG,"The interstitial wasn't loaded yet.");
                                gotoChosePhoto();
                            }
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            super.onAdLoaded(ad);
                            interstitialAd.show();
                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            super.onInterstitialDismissed(ad);
                            gotoChosePhoto();
                        }
                    });
                } else {
                    gotoChosePhoto();
                }
                break;
            case R.id.btn_exit:
                onBackPressed();
                break;
            case R.id.rl_list:
                if (isReadyShowInAds()) {
                    interstitialAd.loadAd();
                    interstitialAd.setAdListener(new AbstractAdListener() {
                        @Override
                        public void onError(Ad ad, AdError error) {
                            super.onError(ad, error);
                            Log.e(TAG, "load Ads Facebook onError()");
                            mInterstitialAd.setAdListener(new AdListener() {
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    gotoListFileCreated();
                                }

                                @Override
                                public void onAdFailedToLoad(int i) {
                                    super.onAdFailedToLoad(i);
                                    Log.e(TAG, "load Admob onError()");
                                    gotoListFileCreated();
                                }
                            });
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else {
                                gotoListFileCreated();
                            }
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            super.onAdLoaded(ad);
                            interstitialAd.show();
                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            super.onInterstitialDismissed(ad);
                            gotoListFileCreated();
                        }
                    });
                } else {
                    gotoListFileCreated();
                }
                break;
            case R.id.ln_share:
                intentShareApp();
                break;
            case R.id.ln_more:
                moreApp();
                break;
            case R.id.ln_rate:
                rateApp();
                break;
        }
    }

    private void rateApp() {
        //todo: rate app
    }

    private void moreApp() {
        //todo: view more app
    }

    private void gotoListFileCreated() {
        checkPermission(new String[]{
                        BaseActivity.PER_READ,
                        BaseActivity.PER_WRITE
                },
                new CallBackPermission() {
                    @Override
                    public void grantedFullPermission() {
                        showLoading();
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
                        BaseActivity.PER_CAMERA,
                        BaseActivity.PER_AUDIO
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

    private void getImageFromGallery() {
        ChooseImageActivity.startIntent(this);
    }

    private void createFolder() {
        if (!pathManager.checkFolderExists(PathManager.FOLDER_IMAGE)) {
            pathManager.createFolderImage();
        }
        if (!pathManager.checkFolderExists(PathManager.FOLDER_VIDEO)) {
            pathManager.createFolderVideo();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        cancleLoading();
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdsUtil.HASHED_ID)
                .build());
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_alert_save);

        TextView txtAlert = dialog.findViewById(R.id.txt_alert);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnSave.setText(getResources().getString(R.string.exit));
        txtAlert.setText(getResources().getString(R.string.ques_exit));
        btnSave.setOnClickListener(v -> super.onBackPressed());
        btnCancel.setOnClickListener(v -> {
            if (dialog.isShowing()) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
