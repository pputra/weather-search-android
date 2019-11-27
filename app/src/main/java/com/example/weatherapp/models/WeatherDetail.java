package com.example.weatherapp.models;

public class WeatherDetail extends Weather {
    private double precipitation;
    private double cloudCover;
    private double ozone;

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

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }
}
