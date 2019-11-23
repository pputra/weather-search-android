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
public class WeeklyFragment extends Fragment {
    private TextView mTextViewMessage;


    public WeeklyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);

        mTextViewMessage = view.findViewById(R.id.tv_weekly_message);
        mTextViewMessage.setText("this is WeeklyFragment");

        return view;
    }

}
