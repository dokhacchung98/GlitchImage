package com.khacchung.glitchimage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.khacchung.glitchimage.R;
import com.khacchung.glitchimage.activity.ListFileActivity;
import com.khacchung.glitchimage.adapter.ViewPagerAdapter;
import com.khacchung.glitchimage.base.BaseActivity;
import com.khacchung.glitchimage.customs.CallBackClick;

public class ListFileFragment extends Fragment {
    private BaseActivity baseActivity;
    private CallBackClick callBackClick;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageCreatedFragment imageCreatedFragment;
    private VideoCreatedFragment videoCreatedFragment;

    public ListFileFragment(BaseActivity baseActivity,
                            CallBackClick callBackClick,
                            ImageCreatedFragment imageCreatedFragment,
                            VideoCreatedFragment videoCreatedFragment
    ) {
        this.baseActivity = baseActivity;
        this.callBackClick = callBackClick;
        this.imageCreatedFragment = imageCreatedFragment;
        this.videoCreatedFragment = videoCreatedFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_file, container, false);
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPagerAdapter = new ViewPagerAdapter(baseActivity, baseActivity.getSupportFragmentManager(),
                imageCreatedFragment, videoCreatedFragment);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if (tabLayout.getTabCount() == 2) {
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_picture);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_film);
        }
        if (ListFileActivity.isVideo) {
            viewPager.setCurrentItem(1);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
