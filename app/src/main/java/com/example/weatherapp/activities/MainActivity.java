package com.example.weatherapp.activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPagerFavorite;
    private ViewPagerSummaryAdapter mViewPagerSummaryAdapter;
    private LinearLayout mDotsSlider;
    private ImageView[] mImageViewDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPagerFavorite = findViewById(R.id.view_pager_favorite);
        mDotsSlider = findViewById(R.id.dots_slider);
        mViewPagerSummaryAdapter = new ViewPagerSummaryAdapter(getSupportFragmentManager());
        mViewPagerFavorite.setAdapter(mViewPagerSummaryAdapter);
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

        mViewPagerFavorite.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                    double lat = response.getDouble("lat");
                    double lon = response.getDouble("lon");
                    final Weather weather = new Weather(city, lat, lon);
                    weather.setState(state);
                    weather.setCountry(country);
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
                    JSONArray dailyDataList = response.getJSONObject("daily").getJSONArray("data");

                    weather.setIcon(currentlyObj.getString("icon"));
                    weather.setTemperature((int) currentlyObj.getDouble("temperature"));
                    weather.setSummary(currentlyObj.getString("summary"));
                    weather.setHumidity(currentlyObj.getDouble("humidity"));
                    weather.setWindSpeed(currentlyObj.getDouble("windSpeed"));
                    weather.setVisibility(currentlyObj.getDouble("visibility"));
                    weather.setPressure(currentlyObj.getDouble("pressure"));
                    weather.setDailyDataList(dailyDataList);

                    mViewPagerSummaryAdapter.addFavCity(weather, 0);

                    //TODO: replace these data with actual favorites
                    Weather weather1 = new Weather("Seattle", 10, 10);
                    weather1.setIcon("rain");
                    weather1.setTemperature(45);
                    weather1.setSummary("raining");
                    weather1.setHumidity(3);
                    weather1.setWindSpeed(3);
                    weather1.setVisibility(1);
                    weather1.setPressure(1000);
                    weather1.setDailyDataList(dailyDataList);
                    weather1.setCountry("US");
                    weather1.setState("WA");


                    mViewPagerSummaryAdapter.addFavCity(weather1);
                    mViewPagerSummaryAdapter.addFavCity(weather);

                    // TODO: get favorite cities from SharedPreferences and fetch the weather data
                    setDotsSlider();

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

    public void removeFromFavoriteByIndex(int index) {
        mViewPagerSummaryAdapter.removeFavCity(index);
        mViewPagerFavorite.setAdapter(mViewPagerSummaryAdapter);
        mViewPagerFavorite.setCurrentItem(index - 1);

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

        mImageViewDots[mViewPagerFavorite.getCurrentItem()].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot ));
    }
}
