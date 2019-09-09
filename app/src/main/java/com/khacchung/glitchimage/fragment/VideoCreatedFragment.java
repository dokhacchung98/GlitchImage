package com.khacchung.glitchimage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.adapter.ImageAdapter;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackClick;

import java.util.ArrayList;

public class VideoCreatedFragment extends Fragment {
    private BaseActivity baseActivity;
    private ArrayList<String> listUriImages;
    private RecyclerView recyclerViewImage;
    private ImageAdapter imageAdapter;
    private CallBackClick callBackClick;
    private TextView txtEmpty;

    public VideoCreatedFragment(BaseActivity activity,
                                ArrayList<String> listUriImages,
                                CallBackClick callBackClick) {
        this.baseActivity = activity;
        this.listUriImages = listUriImages;
        this.callBackClick = callBackClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        recyclerViewImage = view.findViewById(R.id.rcv_image);
        txtEmpty = view.findViewById(R.id.txt_empty);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        imageAdapter = new ImageAdapter(baseActivity, listUriImages, callBackClick);
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(baseActivity, 2));
        txtEmpty.setVisibility(listUriImages.isEmpty() ? View.VISIBLE : View.GONE);
    }
}