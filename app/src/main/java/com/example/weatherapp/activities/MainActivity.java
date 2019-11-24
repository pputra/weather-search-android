package com.example.weatherapp.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.ViewPagerFavoriteAdapter;
import com.example.weatherapp.models.Weather;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void fetchWeatherData() {
        fetchCurrLocation();
        List<Weather> favCityList = new ArrayList<>();
        Weather currWeather = new Weather("Los Angeles", 34.0322, -118.2836);
        Weather dummyWeather = new Weather("San Diego", 34.0322, -118.2836);
        favCityList.add(currWeather);
        favCityList.add(dummyWeather);

        //TODO: fetch current location and favorited cities from SharedPreferences
        mViewPagerFavoriteAdapter.setFavCityList(favCityList);
    }

    public void fetchCurrLocation() {
        String url = "http://ip-api.com/json";
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    response.getString("city"),
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }
}
