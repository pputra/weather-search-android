package com.example.weatherapp.utils;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public class Callbacks {
    public interface VolleyCallback{
        void onSuccess(Context context, JSONObject response);
        void onError(Context context, VolleyError error);
    }
}
