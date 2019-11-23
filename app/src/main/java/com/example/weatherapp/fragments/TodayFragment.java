package com.example.weatherapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {
    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_today, container, false);

        // this is how we get a key from intent
        // tv__message.setText(getArguments().getString("message"));

        return view;
    }

}
