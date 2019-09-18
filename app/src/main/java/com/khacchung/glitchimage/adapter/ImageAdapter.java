package com.khacchung.glitchimage.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackClick;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private BaseActivity baseActivity;
    private CallBackClick callBack;
    private ArrayList<String> listImages;

    public ImageAdapter(BaseActivity baseActivity, ArrayList<String> listImages, CallBackClick callBack) {
        this.baseActivity = baseActivity;
        this.listImages = listImages;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(baseActivity);
        View view = inflater.inflate(R.layout.item_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imgThumbnail.setOnClickListener(view -> callBack.ClickImage(position));
        holder.imgThumbnail.setImageURI(Uri.parse(listImages.get(position)));
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
        }
    }
}
