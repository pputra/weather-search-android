package com.example.weatherapp.activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.ViewPagerFavoriteAdapter;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.utils.Callbacks;
import com.example.weatherapp.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
                "fetching weather data....",
                Toast.LENGTH_SHORT);

        toast.show();

        fetchWeatherData();
    }

    public void fetchWeatherData() {
        mViewPagerFavoriteAdapter.clearFavCity();
        NetworkUtils.fetchCurrLocation(this, new Callbacks.VolleyCallback() {
            @Override
            public void onSuccess(Context context, JSONObject response) {
                try {
                    Weather weather = new Weather(response.getString("city"), response.getDouble("lat"), response.getDouble("lon"));
                    mViewPagerFavoriteAdapter.addFavCity(weather, 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Context context, VolleyError error) {}
        });

        NetworkUtils.fetchCurrLocation(this, new Callbacks.VolleyCallback() {
            @Override
            public void onSuccess(Context context, JSONObject response) {
                try {
                    Weather weather = new Weather("San Diego", response.getDouble("lat"), response.getDouble("lon"));
                    mViewPagerFavoriteAdapter.addFavCity(weather);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Context context, VolleyError error) {}
        });

        NetworkUtils.fetchCurrLocation(this, new Callbacks.VolleyCallback() {
            @Override
            public void onSuccess(Context context, JSONObject response) {
                try {
                    Weather weather = new Weather("Houston", response.getDouble("lat"), response.getDouble("lon"));
                    mViewPagerFavoriteAdapter.addFavCity(weather);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Context context, VolleyError error) {}
        });

        //TODO: fetch current location and favorited cities from SharedPreferences
    }
}
