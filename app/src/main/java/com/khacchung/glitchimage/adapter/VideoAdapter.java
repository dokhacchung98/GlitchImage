package com.khacchung.glitchimage.adapter;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackClick;
import com.khacchung.glitchimage.customs.RemoveCallBack;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private BaseActivity baseActivity;
    private CallBackClick callBack;
    private ArrayList<String> listVieos;

    public VideoAdapter(BaseActivity baseActivity,
                        ArrayList<String> listImages,
                        CallBackClick callBack) {
        this.baseActivity = baseActivity;
        this.listVieos = listImages;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(baseActivity);
        View view = inflater.inflate(R.layout.item_video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(listVieos.get(position),
                MediaStore.Video.Thumbnails.MINI_KIND);

        holder.imgThumbnail.setOnClickListener(view -> callBack.ClickVideo(position));
        holder.imgThumbnail.setImageBitmap(bitmap);
        holder.btnRemove.setOnClickListener(v -> baseActivity.removeFile(listVieos.get(position),
                new RemoveCallBack() {
                    @Override
                    public void removeFileSuccess() {
                        listVieos.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void noRemove() {

                    }
                }));
    }

    @Override
    public int getItemCount() {
        return listVieos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;
        ImageButton btnRemove;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnRemove = itemView.findViewById(R.id.btn_remove);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
        }
    }
}
