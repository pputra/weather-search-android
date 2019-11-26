package com.example.weatherapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.activities.DetailWeatherActivity;
import com.example.weatherapp.models.Weather;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
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

    Map<String, MaterialDrawableBuilder.IconValue> mIconMap = new HashMap<>();

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_favorite, container, false);

        initViews(view);

        Weather weather = (Weather) getArguments().getSerializable("Weather");

        setTopCard(weather);
        setMidCard(weather);
        setBotCard(weather);

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
        mTextViewWeatherLocation.setText(weather.getCity());
        mIconWeatherSummary.setIcon(mIconMap.get(weather.getIcon()));

        if (weather.getIcon().equals("clear-day")) mIconWeatherSummary.setColor(Color.YELLOW);

        mTextViewWeatherTemperature.setText(weather.getTemperature() + "°F");
        mTextViewWeatherSummary.setText(weather.getSummary());

        mLayoutTopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThatShowsDetailWeatherActivity = new Intent(getContext(), DetailWeatherActivity.class);
                intentThatShowsDetailWeatherActivity.putExtra("ACTION_BAR_TITLE", weather.getCity());
                startActivity(intentThatShowsDetailWeatherActivity);
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
        for (Weather.DailyData dailyData : weather.getDailyDataList()) {
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

            mLayoutDailyData.addView(dailyDataView);
        }
    }

}
