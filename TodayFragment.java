package com.example.weatherapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.models.WeatherDetail;
import com.example.weatherapp.utils.NumberParser;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {
    TextView mTextViewTodayWindSpeed;
    TextView mTextViewTodayPressure;
    TextView mTextViewTodayPrecipitation;
    TextView mTextViewTodayTemperature;
    ImageView mImageViewTodaySummary;
    TextView mTextViewTodaySummary;
    TextView mTextViewTodayHumidity;
    TextView mTextViewTodayVisibility;
    TextView mTextViewTodayCloudCover;
    TextView mTextViewTodayOzone;
    Map<String, Integer> mIconMap = Weather.getIconMap();


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
        mImageViewTodaySummary = view.findViewById(R.id.ic_today_summary);
        mTextViewTodaySummary = view.findViewById(R.id.tv_today_summary);
        mTextViewTodayHumidity = view.findViewById(R.id.tv_today_humidity);
        mTextViewTodayVisibility = view.findViewById(R.id.tv_today_visibility);
        mTextViewTodayCloudCover = view.findViewById(R.id.tv_today_cloud_cover);
        mTextViewTodayOzone = view.findViewById(R.id.tv_today_ozone);
    }

    private void setViews(WeatherDetail weatherDetail) {
        if (weatherDetail.getWindSpeed() != null) {
            mTextViewTodayWindSpeed.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getWindSpeed()) + " mph");
        } else {
            mTextViewTodayWindSpeed.setText("N/A");
        }

        if (weatherDetail.getPressure() != null) {
            mTextViewTodayPressure.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getPressure()) + " mb");
        } else {
            mTextViewTodayPressure.setText("N/A");
        }

        if (weatherDetail.getPrecipitation() != null) {

            mTextViewTodayPrecipitation.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getPrecipitation()) + " mmph");
        } else {
            mTextViewTodayPrecipitation.setText("N/A");
        }


        mTextViewTodayTemperature.setText(weatherDetail.getTemperature() + "°F");
        mImageViewTodaySummary.setImageResource(mIconMap.get(weatherDetail.getIcon()));
        mTextViewTodaySummary.setText(weatherDetail.getSummary());

        if (weatherDetail.getHumidity() != null) {
            mTextViewTodayHumidity.setText(Integer.toString(NumberParser.convertDecimalToPercentage(weatherDetail.getHumidity())) + "%");
        } else {
            mTextViewTodayHumidity.setText("N/A");
        }

        if (weatherDetail.getVisibility() != null) {
            mTextViewTodayVisibility.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getVisibility()) + " km");
        } else {
            mTextViewTodayVisibility.setText("N/A");
        }

        if (weatherDetail.getCloudCover() != null) {
            mTextViewTodayCloudCover.setText(Integer.toString(NumberParser.convertDecimalToPercentage(weatherDetail.getCloudCover())) + "%");
        } else {
            mTextViewTodayCloudCover.setText("N/A");
        }

        if (weatherDetail.getOzone() != null) {
            mTextViewTodayOzone.setText(NumberParser.convertNumberToTwoDecimalPLacesString(weatherDetail.getOzone()) + " DU");
        } else {
            mTextViewTodayOzone.setText("N/A");
        }
    }

}
