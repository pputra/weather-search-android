package com.example.weatherapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.activities.DetailWeatherActivity;
import com.example.weatherapp.activities.MainActivity;
import com.example.weatherapp.models.Weather;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {
    TextView mTextViewWeatherLocation;
    MaterialIconView mIconWeatherSummary;
    TextView mTextViewWeatherTemperature;
    TextView mTextViewWeatherSummary;
    TextView mTextViewHumidity;
    TextView mTextViewWindSpeed;
    TextView mTextViewVisibility;
    TextView mTextViewPressure;
    ConstraintLayout mLayoutTopCard;
    LinearLayout mLayoutDailyData;
    FloatingActionButton mFloatingActionButtonFavorite;

    Map<String, MaterialDrawableBuilder.IconValue> mIconMap = new HashMap<>();

    public SummaryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_summary, container, false);

        initViews(view);

        Weather weather = (Weather) getArguments().getSerializable("WEATHER");
        int adapterIndex = getArguments().getInt("ADAPTER_INDEX");

        setTopCard(weather);
        setMidCard(weather);
        setBotCard(weather);
        setFavButton(adapterIndex, weather);

        return view;
    }

    private void initViews(View view) {
        mLayoutTopCard = view.findViewById(R.id.fav_top_card);
        mIconWeatherSummary = view.findViewById(R.id.ic_weather_summary);
        mTextViewWeatherLocation = view.findViewById(R.id.tv_weather_location);
        mTextViewWeatherTemperature = view.findViewById(R.id.tv_weather_temperature);
        mTextViewWeatherSummary = view.findViewById(R.id.tv_weather_summary);
        mTextViewHumidity = view.findViewById(R.id.tv_humidity);
        mTextViewWindSpeed = view.findViewById(R.id.tv_wind);
        mTextViewVisibility = view.findViewById(R.id.tv_visibility);
        mTextViewPressure = view.findViewById(R.id.tv_pressure);
        mLayoutDailyData = view.findViewById(R.id.layout_daily_data);
        mFloatingActionButtonFavorite = view.findViewById(R.id.fab_favorite);

        mIconMap.put("clear-day", MaterialDrawableBuilder.IconValue.WEATHER_SUNNY);
        mIconMap.put("clear-night", MaterialDrawableBuilder.IconValue.WEATHER_NIGHT);
        mIconMap.put("rain", MaterialDrawableBuilder.IconValue.WEATHER_RAINY);
        mIconMap.put("sleet", MaterialDrawableBuilder.IconValue.WEATHER_SNOWY_RAINY);
        mIconMap.put("snow", MaterialDrawableBuilder.IconValue.WEATHER_SNOWY);
        mIconMap.put("wind", MaterialDrawableBuilder.IconValue.WEATHER_WINDY_VARIANT);
        mIconMap.put("fog", MaterialDrawableBuilder.IconValue.WEATHER_FOG);
        mIconMap.put("cloudy", MaterialDrawableBuilder.IconValue.WEATHER_CLOUDY);
        mIconMap.put("partly-cloudy-night", MaterialDrawableBuilder.IconValue.WEATHER_PARTLYCLOUDY);
        mIconMap.put("partly-cloudy-day", MaterialDrawableBuilder.IconValue.WEATHER_PARTLYCLOUDY);
    }

    private void setTopCard(final Weather weather) {
        mTextViewWeatherLocation.setText(weather.getLocation());
        mIconWeatherSummary.setIcon(mIconMap.get(weather.getIcon()));

        if (weather.getIcon().equals("clear-day")) mIconWeatherSummary.setColor(Color.YELLOW);

        mTextViewWeatherTemperature.setText(weather.getTemperature() + "°F");
        mTextViewWeatherSummary.setText(weather.getSummary());

        mLayoutTopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = weather.getLocation();
                int temperature = weather.getTemperature();
                double lat = weather.getLat();
                double lon = weather.getLon();

                goToDetailActivity(location, temperature, lat, lon);
            }
        });
    }

    private void setMidCard(final Weather weather) {
        mTextViewHumidity.setText(Double.toString(weather.getHumidity()));
        mTextViewWindSpeed.setText(Double.toString(weather.getWindSpeed()));
        mTextViewVisibility.setText(Double.toString(weather.getVisibility()));
        mTextViewPressure.setText(Double.toString(weather.getPressure()));
    }

    private void setBotCard(final Weather weather) {
        for (int i = 0; i < weather.getDailyDataList().size(); i++) {
            Weather.DailyData dailyData = weather.getDailyDataList().get(i);
            LayoutInflater dailyDataViewInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout dailyDataView = (LinearLayout) dailyDataViewInflater.inflate(R.layout.daily_data_layout, null);

            TextView textViewDailyDataDate = dailyDataView.findViewById(R.id.tv_daily_data_date);
            textViewDailyDataDate.setText(Integer.toString(dailyData.getTime()));

            MaterialIconView materialIconViewDailyDataIcon = dailyDataView.findViewById(R.id.ic_daily_data);
            materialIconViewDailyDataIcon.setIcon(mIconMap.get(dailyData.getIcon()));
            if (dailyData.getIcon().equals("clear-day")) materialIconViewDailyDataIcon.setColor(Color.YELLOW);

            TextView textViewDailyDataMinTemperature = dailyDataView.findViewById(R.id.tv_daily_data_min_temperature);
            textViewDailyDataMinTemperature.setText(Integer.toString(dailyData.getMinTemperature()));

            TextView textViewDailyDataMaxTemperature = dailyDataView.findViewById(R.id.tv_daily_data_max_temperature);
            textViewDailyDataMaxTemperature.setText(Integer.toString(dailyData.getMaxTemperature()));

            if (i == weather.getDailyDataList().size() - 1) {
                LinearLayout linearLayoutBorder = dailyDataView.findViewById(R.id.layout_border_daily_data);
                linearLayoutBorder.setVisibility(View.GONE);
            }

            mLayoutDailyData.addView(dailyDataView);
        }
    }

    private void setFavButton(final int i, final Weather weather) {
        boolean isCurrLocation = i == 0;

        if (isCurrLocation) return;

        mFloatingActionButtonFavorite.show();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        final Set<String> favSet = new HashSet<>(sharedPreferences.getStringSet("FAVORITE", new HashSet<String>()));

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if (favSet.contains(weather.getLocation())) {
            mFloatingActionButtonFavorite.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_map_marker_minus));
        } else {
            mFloatingActionButtonFavorite.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_map_marker_plus));
        }

        boolean setFavButtonForSearchActivity = i == -1;

        if (setFavButtonForSearchActivity) {
            editor.putBoolean("RESET_ADAPTER", true);
            editor.commit();

            mFloatingActionButtonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (favSet.contains(weather.getLocation())) {
                        favSet.remove(weather.getLocation());
                        editor.putStringSet("FAVORITE", favSet);
                        editor.commit();

                        mFloatingActionButtonFavorite.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_map_marker_plus));

                        Toast toast = Toast.makeText(getContext(),
                                weather.getLocation() + " was removed from favorites",
                                Toast.LENGTH_SHORT);

                        toast.show();
                    } else {
                        favSet.add(weather.getLocation());
                        editor.putStringSet("FAVORITE", favSet);
                        editor.commit();

                        mFloatingActionButtonFavorite.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_map_marker_minus));

                        Toast toast = Toast.makeText(getContext(),
                                weather.getLocation() + " was added from favorites",
                                Toast.LENGTH_SHORT);

                        toast.show();
                    }
                }
            });

            return;
        }

        mFloatingActionButtonFavorite.show();
        mFloatingActionButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getContext(),
                        weather.getLocation() + " was removed from favorites",
                        Toast.LENGTH_SHORT);

                toast.show();

                MainActivity activity = (MainActivity) getActivity();
                favSet.remove(weather.getLocation());
                editor.putStringSet("FAVORITE", favSet);
                editor.commit();
                activity.removeFromFavoriteByIndex(i);
            }
        });
    }

    private void goToDetailActivity(String location, int temperature, double lat, double lon) {
        Intent intentThatShowsDetailWeatherActivity = new Intent(getContext(), DetailWeatherActivity.class);
        intentThatShowsDetailWeatherActivity.putExtra("LOCATION", location);
        intentThatShowsDetailWeatherActivity.putExtra("TEMPERATURE", temperature);
        intentThatShowsDetailWeatherActivity.putExtra("LAT", lat);
        intentThatShowsDetailWeatherActivity.putExtra("LON", lon);

        startActivity(intentThatShowsDetailWeatherActivity);
    }
}
