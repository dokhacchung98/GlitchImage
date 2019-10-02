package com.khacchung.glitchimage.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.listener.OnSeekCompletionListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdRequest;
import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.application.MyApplication;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.RemoveCallBack;
import com.khacchung.glitchimage.util.AdsUtil;
import com.khacchung.glitchimage.util.AudienceNetworkInitializeHelper;

public class PreviewActivity extends BaseActivity implements OnPreparedListener, OnSeekCompletionListener {

    private static int CURRENT_TYPE = ListFileActivity.TYPE_IMG;
    private static String TAG = PreviewActivity.class.getSimpleName();

    public static void startIntent(BaseActivity baseActivity, String path, int type) {
        Intent intent = new Intent(baseActivity, PreviewActivity.class);
        intent.putExtra(ListFileActivity.TYPE, type);
        intent.putExtra(ListFileActivity.PATH, path);
        PreviewActivity.CURRENT_TYPE = type;
        baseActivity.startActivity(intent);
        MyApplication.addCountAction();
    }

    private int myType;
    private String myPath;
    private PhotoView photoView;
    private VideoView videoView;
    private AdView adView;
    private com.google.android.gms.ads.AdView mAdView;
    private AdRequest adRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        enableBackButton();
        setTitleToolbar(getResources().getString(R.string.preview));
        Intent intent = getIntent();
        //ads
        AudienceNetworkInitializeHelper.initialize(this);
        //ads
        adView = new AdView(this, AdsUtil.BANNER_ID, AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);
        mAdView = findViewById(R.id.adView);
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

        myPath = intent.getStringExtra(ListFileActivity.PATH);
        myType = intent.getIntExtra(ListFileActivity.TYPE, ListFileActivity.TYPE_IMG);
        videoView = findViewById(R.id.video_views);
        photoView = findViewById(R.id.photo_view);
        if (myType == ListFileActivity.TYPE_IMG) {
            videoView.setVisibility(View.GONE);
            photoView.setImageURI(Uri.parse(myPath));
        } else if (myType == ListFileActivity.TYPE_VIDEO) {
            photoView.setVisibility(View.GONE);
            videoView.setOnPreparedListener(this);
            videoView.setVideoURI(Uri.parse(myPath));
            videoView.seekTo(0);
            videoView.setOnSeekCompletionListener(this);
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
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
        } else if (item.getItemId() == R.id.action_remove) {
            removeFile(myPath);
        } else if (item.getItemId() == R.id.action_edit) {
            if (myType == ListFileActivity.TYPE_IMG) {
                if (isReadyShowInAds()) {
                    interstitialAd.loadAd();
                    interstitialAd.setAdListener(new AbstractAdListener() {
                        @Override
                        public void onError(Ad ad, AdError error) {
                            super.onError(ad, error);
                            runOnUiThread(() -> gotoGlitchImage(myPath));
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            super.onAdLoaded(ad);
                            interstitialAd.show();
                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {
                            super.onInterstitialDismissed(ad);
                            runOnUiThread(() -> gotoGlitchImage(myPath));
                        }
                    });
                } else {
                    runOnUiThread(() -> gotoGlitchImage(myPath));
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (CURRENT_TYPE == ListFileActivity.TYPE_IMG) {
            getMenuInflater().inflate(R.menu.menu_share_remove_edit, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_share_remove, menu);
        }
        return true;
    }

    public void removeFile(String path) {
        removeFile(path, new RemoveCallBack() {
            @Override
            public void removeFileSuccess() {
                finish();
            }

            @Override
            public void noRemove() {

            }
        });
    }

    @Override
    public void onPrepared() {
        videoView.start();
    }

    @Override
    public void onSeekComplete() {
        videoView.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdsUtil.HASHED_ID)
                .build());
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
