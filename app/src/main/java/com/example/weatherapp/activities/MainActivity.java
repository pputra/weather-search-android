package com.example.weatherapp.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.ViewPagerFavoriteAdapter;
import com.example.weatherapp.models.Weather;

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
        List<Weather> favCityList = new ArrayList<>();
        Weather currWeather = new Weather("Los Angeles", 34.0322, -118.2836);
        Weather dummyWeather = new Weather("San Diego", 34.0322, -118.2836);
        favCityList.add(currWeather);
        favCityList.add(dummyWeather);

        //TODO: fetch current location and favorited cities from SharedPreferences
        mViewPagerFavoriteAdapter.setFavCityList(favCityList);
    }
}
