package com.example.weatherapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDetailWeather(View v) {
        Intent intentThatShowDetailWeatherActivity = new Intent(this, DetailWeatherActivity.class);
        startActivity(intentThatShowDetailWeatherActivity);
    }
}
