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
import com.khacchung.glitchimage.adapter.ImageAdapter;
import com.khacchung.glitchimage.application.MyApplication;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackClick;
import com.khacchung.glitchimage.customs.EditListenner;

import java.util.ArrayList;

public class ImageCreatedFragment extends Fragment {
    private static final String TAG = ImageCreatedFragment.class.getSimpleName();
    private BaseActivity baseActivity;
    private ArrayList<String> listUriImages;
    private RecyclerView recyclerViewImage;
    private ImageAdapter imageAdapter;
    private TextView txtEmpty;
    private CallBackClick callBackClick;
    private MyApplication myApplication;
    private EditListenner editListenner;

    public ImageCreatedFragment(BaseActivity activity,
                                ArrayList<String> listUriImages,
                                CallBackClick callBackClick,
                                MyApplication myApplication,
                                EditListenner editListenner) {
        this.baseActivity = activity;
        this.listUriImages = listUriImages;
        this.callBackClick = callBackClick;
        this.myApplication = myApplication;
        this.editListenner = editListenner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        recyclerViewImage = view.findViewById(R.id.rcv_image);
        txtEmpty = view.findViewById(R.id.txt_empty);
        imageAdapter = new ImageAdapter(baseActivity, listUriImages, callBackClick, editListenner);
        recyclerViewImage.setAdapter(imageAdapter);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(baseActivity, 2));
        txtEmpty.setVisibility(listUriImages.isEmpty() ? View.VISIBLE : View.GONE);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    public void updateListImage(ArrayList<String> list) {
        Log.e(TAG, "updateListImage() " + list.size());
        this.listUriImages = list;
        if (imageAdapter != null) {
            imageAdapter.notifyDataSetChanged();
            txtEmpty.setVisibility(listUriImages.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }
}
