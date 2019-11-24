package com.example.weatherapp.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.weatherapp.fragments.FavoriteFragment;
import com.example.weatherapp.models.Weather;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFavoriteAdapter extends FragmentPagerAdapter {
    private List<Weather> mFavCityList = new ArrayList<>();

    public ViewPagerFavoriteAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Weather curr = mFavCityList.get(position);
        bundle.putString("location", curr.getCity());
        bundle.putSerializable("Weather", curr);
        FavoriteFragment favoriteFragment = new FavoriteFragment();

        favoriteFragment.setArguments(bundle);
        return favoriteFragment;
    }

    @Override
    public int getCount() {
        return mFavCityList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        position = position + 1;
        return "Fragment " + position;
    }

    public void addFavCity(Weather weather) {
        mFavCityList.add(weather);
        notifyDataSetChanged();
    }

    public void addFavCity(Weather weather, int i) {
        mFavCityList.add(i, weather);
        notifyDataSetChanged();
    }

    public void clearFavCity() {
        mFavCityList = new ArrayList<>();
        notifyDataSetChanged();
    }
}
