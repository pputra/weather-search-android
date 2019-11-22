package com.example.weatherapp.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.weatherapp.fragments.FavoriteFragment;

public class ViewPagerFavoriteAdapter extends FragmentPagerAdapter {

    public ViewPagerFavoriteAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        FavoriteFragment favoriteFragment = new FavoriteFragment();
        return favoriteFragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        position = position + 1;
        return "Fragment " + position;
    }
}
