package com.example.weatherapp.utils;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NetworkUtils {
    public static void fetchCurrLocation(final Context context, final Callbacks.VolleyCallback callback) {
        String url = "http://ip-api.com/json";
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        callback.onSuccess(context, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(context, error);
            }
        });

        queue.add(request);
    }
}
