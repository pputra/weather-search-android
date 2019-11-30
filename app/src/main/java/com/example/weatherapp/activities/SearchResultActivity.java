package com.example.weatherapp.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.ViewPagerSearchResultAdapter;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.utils.Callbacks;
import com.example.weatherapp.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchResultActivity extends AppCompatActivity {
    ViewPager mViewPagerSearchResult;
    LinearLayout mProgressBar;
    ViewPagerSearchResultAdapter mViewPagerSearchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mViewPagerSearchResult = findViewById(R.id.view_pager_search_result);
        mProgressBar = findViewById(R.id.pb_search_result_activity);

        Intent intent = getIntent();
        String location = intent.getStringExtra("SEARCH_KEYWORD");

        setUpActionBar(location);
        fetchWeatherData(location);
    }

    private void setUpActionBar(String title) {
        setTitle(title);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // enable back button
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetchWeatherData(final String location) {
        NetworkUtils.fetchWeatherByFullLocation(location, getApplicationContext(), new Callbacks.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    response = response.getJSONObject("weatherData");
                    JSONObject currentlyObj = response.getJSONObject("currently");
                    JSONArray dailyDataList = response.getJSONObject("daily").getJSONArray("data");

                    Weather weather = new Weather(location);

                    weather.setLat(response.getDouble("latitude"));
                    weather.setLon(response.getDouble("longitude"));
                    weather.setIcon(currentlyObj.getString("icon"));
                    weather.setTemperature((int) currentlyObj.getDouble("temperature"));
                    weather.setSummary(currentlyObj.getString("summary"));
                    weather.setHumidity(currentlyObj.getDouble("humidity"));
                    weather.setWindSpeed(currentlyObj.getDouble("windSpeed"));
                    weather.setVisibility(currentlyObj.getDouble("visibility"));
                    weather.setPressure(currentlyObj.getDouble("pressure"));
                    weather.setDailyDataList(dailyDataList);

                    mViewPagerSearchResultAdapter = new ViewPagerSearchResultAdapter(getSupportFragmentManager());
                    mViewPagerSearchResultAdapter.setWeather(weather);

                    mViewPagerSearchResult.setAdapter(mViewPagerSearchResultAdapter);
                    toggleProgressBar(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    private void toggleProgressBar(boolean show) {
        if (show) {
            mProgressBar.setVisibility(View.VISIBLE);
            mViewPagerSearchResult.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mViewPagerSearchResult.setVisibility(View.VISIBLE);
        }
    }
}
