package com.khacchung.glitchimage.adapter;

import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackClick;

import java.util.ArrayList;

public class SlideImageAdapter extends PagerAdapter {
    private ArrayList<String> listImages;
    private LayoutInflater layoutInflater;
    private BaseActivity baseActivity;
    private CallBackClick callBackClick;

    public SlideImageAdapter(BaseActivity baseActivity,
                             ArrayList<String> listImages,
                             CallBackClick callBackClick) {
        this.listImages = listImages;
        this.baseActivity = baseActivity;
        this.callBackClick = callBackClick;
        layoutInflater = LayoutInflater.from(baseActivity);
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = layoutInflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.image);


        imageView.setImageURI(Uri.parse(listImages.get(position)));
        imageView.setOnClickListener(view1 -> callBackClick.ClickImage(position));

        view.addView(imageLayout, 0);

        return imageLayout;
    }
}
