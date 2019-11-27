package com.example.weatherapp.models;

import java.util.List;

public class WeatherDetail extends Weather {
    private double precipitation;
    private double cloudCover;
    private double ozone;
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

    public void setPhotosUrlList(List<String> photosUrlList) {
        this.photosUrlList = photosUrlList;
    }
}
