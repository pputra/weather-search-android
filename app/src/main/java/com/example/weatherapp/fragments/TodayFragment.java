package com.example.weatherapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.WeatherDetail;
import com.example.weatherapp.utils.NumberParser;

import net.steamcrafted.materialiconlib.MaterialIconView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {
    TextView mTextViewTodayWindSpeed;
    TextView mTextViewTodayPressure;
    TextView mTextViewTodayPrecipitation;
    TextView mTextViewTodayTemperature;
    MaterialIconView mIconTodaySummary;
    TextView mTextViewTodaySummary;
    TextView mTextViewTodayHumidity;
    TextView mTextViewTodayVisibility;
    TextView mTextViewTodayCloudCover;
    TextView mTextViewTodayOzone;


    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_today, container, false);

        initViews(view);

        WeatherDetail weatherDetail = (WeatherDetail) getArguments().getSerializable("WEATHER_DETAIL");

        setViews(weatherDetail);

        return view;

    }

    private void initViews(View view) {
        mTextViewTodayWindSpeed = view.findViewById(R.id.tv_today_windspeed);
        mTextViewTodayPressure = view.findViewById(R.id.tv_today_pressure);
        mTextViewTodayPrecipitation = view.findViewById(R.id.tv_today_precipitation);
        mTextViewTodayTemperature = view.findViewById(R.id.tv_today_temperature);
        mIconTodaySummary = view.findViewById(R.id.ic_today_summary);
        mTextViewTodaySummary = view.findViewById(R.id.tv_today_summary);
        mTextViewTodayHumidity = view.findViewById(R.id.tv_today_humidity);
        mTextViewTodayVisibility = view.findViewById(R.id.tv_today_visibility);
        mTextViewTodayCloudCover = view.findViewById(R.id.tv_today_cloud_cover);
        mTextViewTodayOzone = view.findViewById(R.id.tv_today_ozone);
    }

    private void setViews(WeatherDetail weatherDetail) {
        mTextViewTodayWindSpeed.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getWindSpeed()) + " mph");
        mTextViewTodayPressure.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getPressure()) + " mb");
        mTextViewTodayPrecipitation.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getPrecipitation()) + " mmph");
        mTextViewTodayTemperature.setText(weatherDetail.getTemperature() + "°F");

        mTextViewTodaySummary.setText(weatherDetail.getSummary());
        mTextViewTodayHumidity.setText(Integer.toString(NumberParser.convertDecimalToPercentage(weatherDetail.getHumidity())) + "%");

        mTextViewTodayVisibility.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getVisibility()) + " km");
        mTextViewTodayCloudCover.setText(Integer.toString(NumberParser.convertDecimalToPercentage(weatherDetail.getCloudCover())) + "%");
        mTextViewTodayOzone.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getOzone()) + " DU");
    }

}
