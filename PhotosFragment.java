package com.example.weatherapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.RecyclerViewPhotosAdapter;
import com.example.weatherapp.models.WeatherDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment {
    RecyclerView mRecyclerViewPhotos;
    RecyclerViewPhotosAdapter mPhotosAdapter;

    public PhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        mRecyclerViewPhotos = view.findViewById(R.id.rv_photos);
        mRecyclerViewPhotos.setLayoutManager(new LinearLayoutManager(getContext()));

        WeatherDetail weatherDetail = (WeatherDetail) getArguments().getSerializable("WEATHER_DETAIL");

        mPhotosAdapter = new RecyclerViewPhotosAdapter(weatherDetail.getPhotosUrlList());
        mRecyclerViewPhotos.setAdapter(mPhotosAdapter);

        return view;
    }

}
