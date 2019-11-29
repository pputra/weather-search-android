package com.example.weatherapp.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.weatherapp.fragments.SummaryFragment;
import com.example.weatherapp.models.Weather;

public class ViewPagerSearchResultAdapter extends FragmentPagerAdapter {
    Weather mWeather;

    public ViewPagerSearchResultAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("ADAPTER_INDEX", position);
        bundle.putSerializable("WEATHER", mWeather);
        SummaryFragment summaryFragment = new SummaryFragment();

        summaryFragment.setArguments(bundle);
        return summaryFragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    public void setWeather(Weather weather) {
        mWeather = weather;
    }
}
