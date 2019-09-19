package com.khacchung.glitchimage.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.fragment.ImageCreatedFragment;
import com.khacchung.glitchimage.fragment.VideoCreatedFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private BaseActivity baseActivity;

    private ImageCreatedFragment imageCreatedFragment;
    private VideoCreatedFragment videoCreatedFragment;

    public ViewPagerAdapter(BaseActivity activity,
                            FragmentManager fm,
                            ImageCreatedFragment imageCreatedFragment,
                            VideoCreatedFragment videoCreatedFragment) {
        super(fm);
        this.baseActivity = activity;
        this.imageCreatedFragment = imageCreatedFragment;
        this.videoCreatedFragment = videoCreatedFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = imageCreatedFragment;
                break;
            case 1:
                fragment = videoCreatedFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Images";
                break;
            case 1:
                title = "Video";
        }
        return title;
    }
}
