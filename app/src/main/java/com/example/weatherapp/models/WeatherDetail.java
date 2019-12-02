package com.example.weatherapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDetail extends Weather {
    private double precipitation;
    private double cloudCover;
    private double ozone;
    List<Integer> dailyMinTemperatures;
    List<Integer> dailyMaxTemperatures;
    List<String> photosUrlList;

    public WeatherDetail(String city, double lat, double lon) {
        super(city, lat, lon);
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public double getOzone() {
        return ozone;
    }

    public List<Integer> getDailyMinTemperatures() {
        return dailyMinTemperatures;
    }

    public List<Integer> getDailyMaxTemperatures() {
        return dailyMaxTemperatures;
    }

    public List<String> getPhotosUrlList() {
        return photosUrlList;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    public void setDailyTemperatures(JSONArray dailyDatalist) throws JSONException {
        dailyMinTemperatures = new ArrayList<>();
        dailyMaxTemperatures = new ArrayList<>();

        for (int i = 0; i < dailyDatalist.length(); i++) {
            JSONObject dailyData = dailyDatalist.getJSONObject(i);

            int minTemperature = (int) Math.round(dailyData.getDouble("temperatureLow"));
            int maxTemperature = (int) Math.round(dailyData.getDouble("temperatureHigh"));

            dailyMinTemperatures.add(minTemperature);
            dailyMaxTemperatures.add(maxTemperature);
        }
    }

    public void setPhotosUrlList(List<String> photosUrlList) {
        this.photosUrlList = photosUrlList;
    }
}
