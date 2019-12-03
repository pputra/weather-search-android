package com.example.weatherapp.utils;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NetworkUtils {
    //private static final String DEFAULT_HOST = "http://10.26.190.55:3000/api/";
    private static final String DEFAULT_HOST = "http://192.168.1.4:3000/api";

    public static void fetchCurrLocation(Context context, final Callbacks.VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://ip-api.com/json";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        queue.add(request);
    }

    public static void fetchWeatherByCoordinate(double lat, double lon, Context context, final Callbacks.VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Uri.Builder uri = Uri.parse(DEFAULT_HOST).buildUpon().appendPath("weather");
        uri.appendQueryParameter("lat", Double.toString(lat));
        uri.appendQueryParameter("lon", Double.toString(lon));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        queue.add(request);
    }

    public static void fetchWeatherByCoordinate(double lat, double lon, String photosKeyWord, Context context, final Callbacks.VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Uri.Builder uri = Uri.parse(DEFAULT_HOST).buildUpon().appendPath("weather");
        uri.appendQueryParameter("lat", Double.toString(lat));
        uri.appendQueryParameter("lon", Double.toString(lon));
        uri.appendQueryParameter("photos_keyword", photosKeyWord);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        queue.add(request);
    }


    public static void fetchWeatherByFullLocation(String location, Context context, final Callbacks.VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Uri.Builder uri = Uri.parse(DEFAULT_HOST).buildUpon().appendPath("weather");
        uri.appendQueryParameter("location", location);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        queue.add(request);
    }

    public static void fetchLocationAutoCompleteSuggestion(String keyword, Context context, final Callbacks.VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Uri.Builder uri = Uri.parse(DEFAULT_HOST).buildUpon().appendPath("places");
        uri.appendQueryParameter("input", keyword);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        queue.add(request);
    }
}
