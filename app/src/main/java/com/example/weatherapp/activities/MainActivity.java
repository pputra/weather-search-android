package com.example.weatherapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.ViewPagerSummaryAdapter;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.utils.Callbacks;
import com.example.weatherapp.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPagerSummary;
    private ViewPagerSummaryAdapter mViewPagerSummaryAdapter;
    private LinearLayout mProgressBar;
    private LinearLayout mDotsSlider;
    private ImageView[] mImageViewDots;
    private int numLoadedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPagerSummary = findViewById(R.id.view_pager_favorite);
        mProgressBar = findViewById(R.id.pb_main_activity);
        mDotsSlider = findViewById(R.id.dots_slider);
        mViewPagerSummaryAdapter = new ViewPagerSummaryAdapter(getSupportFragmentManager());
        mViewPagerSummary.setAdapter(mViewPagerSummaryAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchMenu = menu.findItem(R.id.action_bar_search);

        SearchView searchView = (SearchView) searchMenu.getActionView();

        searchView.setBackgroundColor(getResources().getColor(R.color.colorSecondaryDark));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intentThatShowsSearchResultActivity = new Intent(getApplicationContext(), SearchResultActivity.class);
                intentThatShowsSearchResultActivity.putExtra("SEARCH_KEYWORD", query);

                startActivity(intentThatShowsSearchResultActivity);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO : FETCH API AND AUTOCOMPLETE
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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

    private void setDotsSlider() {
        resetDotSlider();

        mViewPagerSummary.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                int numDots = mViewPagerSummaryAdapter.getCount();

                for (int i = 0; i < numDots; i++) {
                    mImageViewDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                mImageViewDots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot ));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void showWeatherData() {
        mViewPagerSummaryAdapter.clearFavCity();
        NetworkUtils.fetchCurrLocation(getApplicationContext(), new Callbacks.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    String city = response.getString("city");
                    String state = response.getString("region");
                    String country = response.getString("countryCode");

                    String location = city + ", " + state + ", " + country;

                    double lat = response.getDouble("lat");
                    double lon = response.getDouble("lon");
                    final Weather weather = new Weather(location, lat, lon);
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
        numLoadedData = 0;
        toggleProgressBar(true);
        NetworkUtils.fetchWeatherByCoordinate(lat, lon, getApplicationContext(), new Callbacks.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    response = response.getJSONObject("weatherData");
                    JSONObject currentlyObj = response.getJSONObject("currently");
                    JSONArray dailyDataList = response.getJSONObject("daily").getJSONArray("data");

                    weather.setIcon(currentlyObj.getString("icon"));
                    weather.setTemperature((int) currentlyObj.getDouble("temperature"));
                    weather.setSummary(currentlyObj.getString("summary"));
                    weather.setHumidity(currentlyObj.getDouble("humidity"));
                    weather.setWindSpeed(currentlyObj.getDouble("windSpeed"));
                    weather.setVisibility(currentlyObj.getDouble("visibility"));
                    weather.setPressure(currentlyObj.getDouble("pressure"));
                    weather.setDailyDataList(dailyDataList);

                    mViewPagerSummaryAdapter.addFavCity(weather);

                    fetchWeatherDataFromFavoriteLocation();
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

    private void fetchWeatherDataFromFavoriteLocation() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Set<String> favSet = new HashSet<>(sharedPreferences.getStringSet("FAVORITE", new HashSet<String>()));

        mViewPagerSummary.setOffscreenPageLimit(favSet.size() + 1);

        if (favSet.isEmpty()) {
            toggleProgressBar(false);
            setDotsSlider();
        }

        Iterator<String> iterator = favSet.iterator();

        while (iterator.hasNext()) {
            final String favLocation = iterator.next();
            NetworkUtils.fetchWeatherByFullLocation(favLocation, getApplicationContext(), new Callbacks.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        response = response.getJSONObject("weatherData");
                        JSONObject currentlyObj = response.getJSONObject("currently");
                        JSONArray dailyDataList = response.getJSONObject("daily").getJSONArray("data");

                        Weather favLocationWeather = new Weather(favLocation);

                        favLocationWeather.setLat(response.getDouble("latitude"));
                        favLocationWeather.setLon(response.getDouble("longitude"));
                        favLocationWeather.setIcon(currentlyObj.getString("icon"));
                        favLocationWeather.setTemperature((int) currentlyObj.getDouble("temperature"));
                        favLocationWeather.setSummary(currentlyObj.getString("summary"));
                        favLocationWeather.setHumidity(currentlyObj.getDouble("humidity"));
                        favLocationWeather.setWindSpeed(currentlyObj.getDouble("windSpeed"));
                        favLocationWeather.setVisibility(currentlyObj.getDouble("visibility"));
                        favLocationWeather.setPressure(currentlyObj.getDouble("pressure"));
                        favLocationWeather.setDailyDataList(dailyDataList);

                        mViewPagerSummaryAdapter.addFavCity(favLocationWeather);
                        numLoadedData++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        if (numLoadedData == favSet.size()) {
                            boolean resetAdapter = sharedPreferences.getBoolean("RESET_ADAPTER", false);

                            if (resetAdapter) {
                                mViewPagerSummary.setAdapter(mViewPagerSummaryAdapter);
                                editor.remove("RESET_ADAPTER");
                                editor.commit();
                            }

                            setDotsSlider();
                            toggleProgressBar(false);
                        }
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    error.printStackTrace();
                }
            });
        }
    }

    public void removeFromFavoriteByIndex(int index) {
        mViewPagerSummaryAdapter.removeFavCity(index);
        mViewPagerSummary.setAdapter(mViewPagerSummaryAdapter);
        mViewPagerSummary.setCurrentItem(index - 1);

        resetDotSlider();
    }

    private void resetDotSlider() {
        int numDots = mViewPagerSummaryAdapter.getCount();

        if (numDots == 0) {
            mDotsSlider.removeAllViews();
            return;
        }

        mImageViewDots = new ImageView[numDots];
        mDotsSlider.removeAllViews();

        for (int i = 0; i < numDots; i++) {
            mImageViewDots[i] = new ImageView(this);
            mImageViewDots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(16, 0,16, 0);

            mDotsSlider.addView(mImageViewDots[i], params);
        }

        if (mViewPagerSummary.getCurrentItem() < numDots) {
            mImageViewDots[mViewPagerSummary.getCurrentItem()].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot ));
        }
    }

    private void toggleProgressBar(boolean show) {
        if (show) {
            mProgressBar.setVisibility(View.VISIBLE);
            mViewPagerSummary.setVisibility(View.INVISIBLE);
            mDotsSlider.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mViewPagerSummary.setVisibility(View.VISIBLE);
            mDotsSlider.setVisibility(View.VISIBLE);
        }
    }
}
