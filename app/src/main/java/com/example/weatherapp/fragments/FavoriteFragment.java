package com.example.weatherapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.activities.DetailWeatherActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    TextView mtextViewWeatherLocation;
    ConstraintLayout mLayoutTopCard;
    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_favorite, container, false);

        mLayoutTopCard = view.findViewById(R.id.fav_top_card);
        mtextViewWeatherLocation = view.findViewById(R.id.tv_weather_location);
        mtextViewWeatherLocation.setText(getArguments().getString("location"));

        mLayoutTopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThatShowsDetailWeatherActivity = new Intent(view.getContext(), DetailWeatherActivity.class);
                intentThatShowsDetailWeatherActivity.putExtra("ACTION_BAR_TITLE", getArguments().getString("location"));
                startActivity(intentThatShowsDetailWeatherActivity);
            }
        });

        return view;
    }

}
