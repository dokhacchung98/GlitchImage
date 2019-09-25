package com.khacchung.glitchimage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.activity.ChooseImageActivity;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.models.MyImage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ImageChooseAdapter extends RecyclerView.Adapter<ImageChooseAdapter.MyViewHolder> {
    private ArrayList<MyImage> listImage;
    private BaseActivity activity;

    public ImageChooseAdapter(BaseActivity activity, ArrayList<MyImage> listImage) {
        this.activity = activity;
        this.listImage = listImage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(activity)
                        .inflate(R.layout.row_image, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyImage model = listImage.get(position);
        Picasso.get().load(new File(model.getPathImage())).centerCrop().fit().into(holder.imgThumbnail);
        holder.imgThumbnail.setOnClickListener(v ->
                ((ChooseImageActivity) activity).glitchImage(model.getPathImage())
        );
    }


    @Override
    public int getItemCount() {
        return listImage.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
        }
    }
}
