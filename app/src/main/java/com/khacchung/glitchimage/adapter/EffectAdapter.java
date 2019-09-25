package com.khacchung.glitchimage.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.customs.CallBackEffect;

import java.util.List;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.MyViewHolder> {
    private Context context;
    private int[] icon;
    private List<String> list;
    private int pos = 0;
    private DisplayMetrics displayMetrics;
    private int screenHeight;
    private int screenWidth;

    public EffectAdapter(Context context, List<String> list, int[] icon) {
        this.context = context;
        this.list = list;
        this.icon = icon;
        displayMetrics = context.getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_effect, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position == pos) {
            holder.imgMain2.setVisibility(View.VISIBLE);
            holder.txtName.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.imgMain2.setVisibility(View.GONE);
            holder.txtName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

        holder.imgMain.setImageResource(icon[position]);
        holder.txtName.setText(list.get(position));
        holder.itemView.setOnClickListener(view -> {
            pos = position;
            ((CallBackEffect) context).setEffects(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgMain;
        public ImageView imgMain2;
        public RelativeLayout layout;
        public TextView txtName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tv_name);
            imgMain = itemView.findViewById(R.id.img_main);
            imgMain2 = itemView.findViewById(R.id.img_main1);
            layout = itemView.findViewById(R.id.layout);

            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) layout.getLayoutParams();
            params1.width = (screenWidth * 171) / 1080;
            params1.height = (screenHeight * 196) / 1920;
            layout.setLayoutParams(params1);

            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imgMain.getLayoutParams();
            layoutParams2.width = (screenWidth * 171) / 1080;
            layoutParams2.height = (screenHeight * 196) / 1920;
            imgMain.setLayoutParams(layoutParams2);
            imgMain2.setLayoutParams(layoutParams2);

            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) txtName.getLayoutParams();
            layoutParams3.width = (screenWidth * 171) / 1080;
            layoutParams3.height = (screenHeight * 50) / 1920;
            txtName.setLayoutParams(layoutParams3);
        }
    }
}
