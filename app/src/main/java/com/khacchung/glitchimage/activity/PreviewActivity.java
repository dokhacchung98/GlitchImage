package com.khacchung.glitchimage.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.listener.OnSeekCompletionListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.github.chrisbanes.photoview.PhotoView;
import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.base.BaseActivity;

import java.io.File;

public class PreviewActivity extends BaseActivity implements OnPreparedListener, OnSeekCompletionListener {

    public static void startIntent(BaseActivity baseActivity, String path, int type) {
        Intent intent = new Intent(baseActivity, PreviewActivity.class);
        intent.putExtra(ListFileActivity.TYPE, type);
        intent.putExtra(ListFileActivity.PATH, path);
        baseActivity.startActivity(intent);
    }

    private int myType;
    private String myPath;
    private PhotoView photoView;
    private VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        enableBackButton();
        setTitleToolbar(getResources().getString(R.string.preview));
        Intent intent = getIntent();
        myPath = intent.getStringExtra(ListFileActivity.PATH);
        myType = intent.getIntExtra(ListFileActivity.TYPE, ListFileActivity.TYPE_IMG);
        Log.e("TAG", "Type: " + myType + ", path: " + myPath);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share_remove, menu);
        return true;
    }

    private void removeFile(String path) {
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
            finish();
        });
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
    public void onPrepared() {
        videoView.start();
    }

    @Override
    public void onSeekComplete() {
        videoView.start();
    }
}
