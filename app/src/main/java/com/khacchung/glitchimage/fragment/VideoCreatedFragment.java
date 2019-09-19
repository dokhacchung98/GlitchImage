package com.khacchung.glitchimage.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.khacchung.glitchimage.adapter.VideoAdapter;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackClick;

import java.util.ArrayList;

public class VideoCreatedFragment extends Fragment {
    private static final String TAG = VideoCreatedFragment.class.getSimpleName();
    private BaseActivity baseActivity;
    private ArrayList<String> listVideo;
    private RecyclerView recyclerViewImage;
    private VideoAdapter imageAdapter;
    private CallBackClick callBackClick;
    private TextView txtEmpty;

    public VideoCreatedFragment(BaseActivity activity,
                                ArrayList<String> listUriImages,
                                CallBackClick callBackClick) {
        this.baseActivity = activity;
        this.listVideo = listUriImages;
        this.callBackClick = callBackClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        recyclerViewImage = view.findViewById(R.id.rcv_image);
        txtEmpty = view.findViewById(R.id.txt_empty);
        imageAdapter = new VideoAdapter(baseActivity, listVideo, callBackClick);
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(baseActivity, 2));
        txtEmpty.setVisibility(listVideo.isEmpty() ? View.VISIBLE : View.GONE);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void updateListVideo(ArrayList<String> list) {
        Log.e(TAG, "updateListVideo() " + list.size());
        this.listVideo = list;
        if (imageAdapter != null) {
            imageAdapter.notifyDataSetChanged();
            txtEmpty.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }
}
