package com.example.weatherapp.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.weatherapp.fragments.SummaryFragment;
import com.example.weatherapp.models.Weather;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerSummaryAdapter extends FragmentStatePagerAdapter {
    private List<Weather> mFavCityList = new ArrayList<>();

    public ViewPagerSummaryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Weather curr = mFavCityList.get(position);
        bundle.putInt("ADAPTER_INDEX", position);
        bundle.putSerializable("WEATHER", curr);
        SummaryFragment summaryFragment = new SummaryFragment();

        summaryFragment.setArguments(bundle);
        return summaryFragment;
    }

    @Override
    public int getCount() {
        return mFavCityList.size();
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

    public void removeFavCity(int i) {
        mFavCityList.remove(i);
        notifyDataSetChanged();
    }
}
