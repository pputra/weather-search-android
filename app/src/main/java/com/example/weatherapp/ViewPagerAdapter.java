package com.example.weatherapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        TodayFragment todayFragment = new TodayFragment();
        position = position + 1;
        Bundle bundle = new Bundle();
        bundle.putString("message", "Fragment: " + position);
        todayFragment.setArguments(bundle);

        return todayFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        position = position + 1;
        return "Fragment " + position;
    }
}
