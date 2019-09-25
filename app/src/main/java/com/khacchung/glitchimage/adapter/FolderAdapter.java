package com.khacchung.glitchimage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.activity.ChooseImageActivity;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.models.MyFolder;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {
    private ArrayList<MyFolder> listFolder;
    private BaseActivity activity;

    public FolderAdapter(BaseActivity activity, ArrayList<MyFolder> listFolder) {
        this.activity = activity;
        this.listFolder = listFolder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(activity)
                        .inflate(R.layout.row_folder, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyFolder model = listFolder.get(position);
        holder.txtSizeFolder.setText(model.getSizeItem() + "");
        holder.txtNameFolder.setText(model.getNameFolder());
        if (model.isSelect()) {
            holder.imgFolder.setImageResource(R.drawable.ic_folder_select);
            holder.txtNameFolder.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            holder.lnItem.setBackgroundResource(R.drawable.background_photo_press);
        } else {
            holder.imgFolder.setImageResource(R.drawable.ic_folder);
            holder.txtNameFolder.setTextColor(activity.getResources().getColor(R.color.colorBlack));
            holder.lnItem.setBackgroundColor(activity.getResources().getColor(R.color.colorWhite));
        }
        holder.lnItem.setOnClickListener(v -> {
            selectItem(position);
            notifyDataSetChanged();
            ((ChooseImageActivity) activity).getAllImageFromPathFolder(model.getPathFolder());
        });
    }

    private void selectItem(int pos) {
        for (MyFolder item : listFolder) {
            item.setSelect(false);
        }
        listFolder.get(pos).setSelect(true);
    }

    @Override
    public int getItemCount() {
        return listFolder.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtSizeFolder;
        TextView txtNameFolder;
        ImageView imgFolder;
        LinearLayout lnItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameFolder = itemView.findViewById(R.id.txt_name_folder);
            txtSizeFolder = itemView.findViewById(R.id.txt_size);
            imgFolder = itemView.findViewById(R.id.img_folder);
            lnItem = itemView.findViewById(R.id.ln_item_folder);
        }
    }
}
