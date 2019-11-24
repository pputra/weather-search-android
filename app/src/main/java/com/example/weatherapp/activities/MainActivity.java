package com.example.weatherapp.activities;

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

        // TODO: replace toast with a progressbar
        Toast toast = Toast.makeText(getApplicationContext(),
                "fetching weather data....",
                Toast.LENGTH_SHORT);

        toast.show();

        showWeatherData();
    }

    public void showWeatherData() {
        mViewPagerFavoriteAdapter.clearFavCity();
        NetworkUtils.fetchCurrLocation(getApplicationContext(), new Callbacks.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    String city = response.getString("city");
                    double lat = response.getDouble("lat");
                    double lon = response.getDouble("lon");
                    final Weather weather = new Weather(city, lat, lon);

                    fetchWeatherData(weather, lat, lon);

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

    public void fetchWeatherData(final Weather weather, double lat, final double lon) {
        NetworkUtils.fetchWeatherByCoordinate(lat, lon, getApplicationContext(), new Callbacks.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    response = response.getJSONObject("weatherData");
                    JSONObject currentlyObj = response.getJSONObject("currently");

                    weather.setIcon(currentlyObj.getString("icon"));
                    weather.setTemperature((int) currentlyObj.getDouble("temperature"));
                    weather.setSummary(currentlyObj.getString("summary"));
                    weather.setHumidity(currentlyObj.getDouble("humidity"));
                    weather.setWindSpeed(currentlyObj.getDouble("windSpeed"));
                    weather.setVisibility(currentlyObj.getDouble("visibility"));
                    weather.setPressure(currentlyObj.getDouble("pressure"));

                    mViewPagerFavoriteAdapter.addFavCity(weather, 0);

                    // TODO: get favorite cities from SharedPreferences and fetch the weather data

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
}
