package com.khacchung.glitchimage.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.activity.PictureEffectActivity;
import com.khacchung.glitchimage.application.MyApplication;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackClick;
import com.khacchung.glitchimage.customs.EditListenner;
import com.khacchung.glitchimage.customs.RemoveCallBack;
import com.khacchung.glitchimage.util.AdjustBitmap;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private BaseActivity baseActivity;
    private CallBackClick callBack;
    private ArrayList<String> listImages;
    private int screenHeight;
    private MyApplication myApplication;
    private EditListenner editListenner;

    public ImageAdapter(BaseActivity baseActivity,
                        ArrayList<String> listImages,
                        MyApplication myApplication,
                        CallBackClick callBack,
                        EditListenner editListenner) {
        this.baseActivity = baseActivity;
        this.listImages = listImages;
        this.callBack = callBack;
        this.myApplication = myApplication;
        this.editListenner = editListenner;
        DisplayMetrics displayMetrics = baseActivity.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
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
        Picasso.get().load(new File(listImages.get(position)))
                .centerCrop().fit().into(holder.imgThumbnail);
        holder.btnEdit.setOnClickListener(v -> editListenner.editImageAtPos(position));
        holder.btnRemove.setOnClickListener(v -> baseActivity.removeFile(
                listImages.get(position),
                new RemoveCallBack() {
                    @Override
                    public void removeFileSuccess() {
                        listImages.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void noRemove() {

                    }
                }
        ));
    }

    private void editImage(int position) {
        try {
            Bitmap bitmap = AdjustBitmap.getCorrectlyOrientedImage(baseActivity,
                    Uri.parse(listImages.get(position)), screenHeight);
            myApplication.setImgBMP(bitmap);
            myApplication.setPathImage(listImages.get(position));
            PictureEffectActivity.startIntent(baseActivity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;
        ImageButton btnRemove;
        ImageButton btnEdit;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }
}
