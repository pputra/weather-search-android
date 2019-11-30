package com.example.weatherapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.ViewPagerDetailAdapter;
import com.example.weatherapp.models.WeatherDetail;
import com.example.weatherapp.utils.Callbacks;
import com.example.weatherapp.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailWeatherActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerDetailAdapter viewPagerDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_weather);
        tabLayout = findViewById(R.id.tablayout_detail_tabs);
        viewPager = findViewById(R.id.pager);

        setUpActionBar();

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("LAT", 0);
        double lon = intent.getDoubleExtra("LON", 0);

        WeatherDetail weatherDetail = new WeatherDetail(intent.getStringExtra("LOCATION"), lat, lon);

        fetchWeatherDetailData(weatherDetail, lat, lon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // enable back button
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_bar_tweet:
                String location = getIntent().getStringExtra("LOCATION");
                int temperature = getIntent().getIntExtra("TEMPERATURE", 60);

                tweet(location, temperature);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpActionBar() {
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("LOCATION"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void fetchWeatherDetailData(final WeatherDetail weatherDetail, double lat, final double lon) {
        NetworkUtils.fetchWeatherByCoordinate(lat, lon, weatherDetail.getLocation(), getApplicationContext(), new Callbacks.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject currentlyObj = response.getJSONObject("weatherData").getJSONObject("currently");

                    weatherDetail.setIcon(currentlyObj.getString("icon"));
                    weatherDetail.setTemperature((int) currentlyObj.getDouble("temperature"));
                    weatherDetail.setSummary(currentlyObj.getString("summary"));
                    weatherDetail.setHumidity(currentlyObj.getDouble("humidity"));
                    weatherDetail.setWindSpeed(currentlyObj.getDouble("windSpeed"));
                    weatherDetail.setVisibility(currentlyObj.getDouble("visibility"));
                    weatherDetail.setPressure(currentlyObj.getDouble("pressure"));
                    weatherDetail.setPrecipitation(currentlyObj.getDouble("precipIntensity"));
                    weatherDetail.setCloudCover(currentlyObj.getDouble("cloudCover"));
                    weatherDetail.setOzone(currentlyObj.getDouble("ozone"));

                    JSONArray photos = response.getJSONArray("photos");

                    List<String> photosList = new ArrayList<>();

                    if (photos != null) {
                        for (int i = 0; i < photos.length(); i++) {
                            photosList.add(photos.getString(i));
                        }
                    }

                    weatherDetail.setPhotosUrlList(photosList);

                    setAdapter(weatherDetail);
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

    public void setAdapter(WeatherDetail weatherDetail) {
        viewPagerDetailAdapter = new ViewPagerDetailAdapter(getSupportFragmentManager(), weatherDetail);
        viewPager.setAdapter(viewPagerDetailAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void tweet(String location, int temperature) {
        Intent httpIntent = new Intent(Intent.ACTION_VIEW);

        String tweetContent = "Checkout " + location + "'s Weather! It is " + temperature + "°F!\n#CSCI571WeatherSearch";
        Uri.Builder uri = Uri.parse("https://twitter.com").buildUpon().appendPath("intent").appendPath("tweet");
        uri.appendQueryParameter("text", tweetContent);

        httpIntent.setData(uri.build());

        startActivity(httpIntent);
    }
}
