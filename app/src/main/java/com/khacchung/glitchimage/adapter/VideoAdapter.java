package com.khacchung.glitchimage.adapter;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
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

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private BaseActivity baseActivity;
    private CallBackClick callBack;
    private ArrayList<String> listImages;

    public VideoAdapter(BaseActivity baseActivity, ArrayList<String> listImages, CallBackClick callBack) {
        this.baseActivity = baseActivity;
        this.listImages = listImages;
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
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = baseActivity.getContentResolver()
                .query(Uri.parse(listImages.get(position)), filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(picturePath, MediaStore.Video.Thumbnails.MICRO_KIND);

        holder.imgThumbnail.setOnClickListener(view -> callBack.ClickVideo(position));
        holder.imgThumbnail.setImageBitmap(bitmap);
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
