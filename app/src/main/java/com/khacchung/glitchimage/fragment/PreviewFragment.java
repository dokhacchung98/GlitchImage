package com.khacchung.glitchimage.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.activity.ListFileActivity;
import com.khacchung.glitchimage.activity.PreviewActivity;
import com.khacchung.glitchimage.adapter.SlideImageAdapter;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackClick;
import com.khacchung.glitchimage.customs.UpdateList;

import java.io.File;
import java.util.ArrayList;

public class PreviewFragment extends Fragment implements ViewPager.OnPageChangeListener, CallBackClick, View.OnClickListener {
    private BaseActivity baseActivity;
    private SlideImageAdapter slideImageAdapter;
    private ArrayList<String> listImages;
    private ViewPager viewPager;
    private TextView txtCurrent;
    private ImageButton btnShare;
    private ImageButton btnRemove;
    private int currentPosition = 0;
    private UpdateList updateList;

    public PreviewFragment(BaseActivity baseActivity,
                           ArrayList<String> listImages,
                           UpdateList updateList) {
        this.baseActivity = baseActivity;
        this.listImages = listImages;
        this.updateList = updateList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        slideImageAdapter = new SlideImageAdapter(baseActivity, listImages, this);
        viewPager = view.findViewById(R.id.view_pager_preview);
        txtCurrent = view.findViewById(R.id.txt_current_pos);
        btnRemove = view.findViewById(R.id.btn_remove);
        btnShare = view.findViewById(R.id.btn_share);
        viewPager.setAdapter(slideImageAdapter);
        btnShare.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
        txtCurrent.setText("1/" + listImages.size());
        return view;
    }

    public void update(ArrayList<String> listImages) {
        this.listImages = listImages;
    }

    public void scrollToPos(int pos) {
        if (pos >= 0 && pos < listImages.size()) {
            viewPager.setCurrentItem(pos);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        txtCurrent.setText((position + 1) + "/" + listImages.size());
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void ClickImage(int pos) {
        if (pos >= 0 && pos < listImages.size()) {
            PreviewActivity.startIntent(baseActivity, listImages.get(pos), ListFileActivity.TYPE_IMG);
        }
    }

    @Override
    public void ClickVideo(int pos) {

    }

    @Override
    public void onClick(View view) {
        if (listImages.size() > 0 && currentPosition >= 0 && currentPosition < listImages.size()) {
            switch (view.getId()) {
                case R.id.btn_share:
                    baseActivity.intentShareImage(listImages.get(currentPosition));
                    break;
                case R.id.btn_remove:
                    removeFile(listImages.get(currentPosition));
                    break;
            }
        }
    }

    private void removeFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        baseActivity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE"
                , Uri.fromFile(file)));
        listImages.remove(path);
        updateList.listIsChange(listImages);
        slideImageAdapter.notifyDataSetChanged();
    }
}
