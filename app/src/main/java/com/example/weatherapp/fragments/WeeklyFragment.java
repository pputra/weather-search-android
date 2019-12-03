package com.example.weatherapp.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.models.WeatherDetail;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyFragment extends Fragment {
    private ImageView mImageViewSummary;
    private TextView mTextViewSummary;
    private LineChart mWeeklyChart;
    private Map<String, Integer> mIconMap = Weather.getIconMap();


    public WeeklyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);

        mImageViewSummary = view.findViewById(R.id.ic_weekly);
        mTextViewSummary = view.findViewById(R.id.tv_weekly_summary);
        mWeeklyChart = view.findViewById(R.id.chart_weekly);

        WeatherDetail weatherDetail = (WeatherDetail) getArguments().getSerializable("WEATHER_DETAIL");

        mImageViewSummary.setImageResource(mIconMap.get(weatherDetail.getIcon()));
        mTextViewSummary.setText(weatherDetail.getSummary());

        setChartData(weatherDetail);
        setLegend();
        setLabels();

        return view;
    }

    private void setChartData(WeatherDetail weatherDetail) {
        List<Integer> minTemperatures = weatherDetail.getDailyMinTemperatures();
        List<Integer> maxTemperatures = weatherDetail.getDailyMaxTemperatures();

        int numData = minTemperatures.size();

        List<Entry> minTemperaturesEntries = new ArrayList<>();
        List<Entry> maxTemperatureEntries = new ArrayList<>();

        for (int i = 0; i < numData; i++) {
            minTemperaturesEntries.add(new Entry(i, minTemperatures.get(i)));
            maxTemperatureEntries.add(new Entry(i, maxTemperatures.get(i)));
        }

        LineDataSet minTemperaturesDataSet = new LineDataSet(minTemperaturesEntries, "Minimum Temperatures");
        LineDataSet maxTemperaturesDataSet = new LineDataSet(maxTemperatureEntries,"Maximum Temperatures");

        minTemperaturesDataSet.setColor(ContextCompat.getColor(getContext(), R.color.darkPurple));
        maxTemperaturesDataSet.setColor(ContextCompat.getColor(getContext(), R.color.lightOrange));

        LineData lineData = new LineData();
        lineData.addDataSet(minTemperaturesDataSet);
        lineData.addDataSet(maxTemperaturesDataSet);

        mWeeklyChart.setData(lineData);
        mWeeklyChart.invalidate();

    }

    private void setLegend() {
        Legend legend = mWeeklyChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        legend.setTextSize(14);
    }

    private void setLabels() {
        mWeeklyChart.getXAxis().setTextColor(Color.WHITE);
        mWeeklyChart.getAxisLeft().setTextColor(Color.WHITE);
        mWeeklyChart.getAxisRight().setTextColor(Color.WHITE);
    }

}
