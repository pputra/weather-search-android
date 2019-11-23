package com.example.weatherapp.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.ViewPagerFavoriteAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPagerFavorite;
    private ViewPagerFavoriteAdapter mViewPagerFavoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPagerFavorite = findViewById(R.id.view_pager_favorite);
        mViewPagerFavoriteAdapter = new ViewPagerFavoriteAdapter(getSupportFragmentManager());
        mViewPagerFavorite.setAdapter(mViewPagerFavoriteAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast toast = Toast.makeText(getApplicationContext(),
                "fetching weather data",
                Toast.LENGTH_SHORT);

        toast.show();

        fetchWeatherData();
    }

    public void showDetailWeather(View v) {
        Intent intentThatShowDetailWeatherActivity = new Intent(this, DetailWeatherActivity.class);
        startActivity(intentThatShowDetailWeatherActivity);
    }

    public void fetchWeatherData() {
        List<String> favCityList = new ArrayList<>();
        favCityList.add("Los Angeles, CA, USA");
        favCityList.add("San Diego");

        //TODO: fetch current location and favorited cities from SharedPreferences
        mViewPagerFavoriteAdapter.setFavCityList(favCityList);
    }
}
