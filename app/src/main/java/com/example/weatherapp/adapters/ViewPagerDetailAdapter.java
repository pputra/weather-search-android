package com.example.weatherapp.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.weatherapp.fragments.PhotosFragment;
import com.example.weatherapp.fragments.TodayFragment;
import com.example.weatherapp.fragments.WeeklyFragment;

public class ViewPagerDetailAdapter extends FragmentPagerAdapter {

    public ViewPagerDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0 :
                TodayFragment todayFragment = new TodayFragment();
                position = position + 1;
                bundle.putString("message", "Fragment: " + position);
                todayFragment.setArguments(bundle);

                return todayFragment;
            case 1 :
                WeeklyFragment weeklyFragment = new WeeklyFragment();
                position = position + 1;
                bundle.putString("message", "Fragment: " + position);
                weeklyFragment.setArguments(bundle);

                return weeklyFragment;
            case 2 :
                PhotosFragment photosFragment = new PhotosFragment();
                position = position + 1;
                bundle.putString("message", "Fragment: " + position);
                photosFragment.setArguments(bundle);

                return photosFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String[] TAB_TITLES = new String[]{"TODAY", "WEEKLY", "PHOTOS"};
        return TAB_TITLES[position];
    }
}
