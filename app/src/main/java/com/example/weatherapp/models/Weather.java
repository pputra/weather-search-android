package com.example.weatherapp.models;

public class Weather {
    private String city;
    private String state;
    private String country;
    private double lat;
    private double lon;
    private String icon;
    private int temperature;
    private String summary;
    private double humidity;
    private double windSpeed;
    private double visibility;
    private double pressure;

    public Weather(String city, double lat, double lon) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getIcon() {
        return icon;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getSummary() {
        return summary;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getVisibility() {
        return visibility;
    }

    public double getPressure() {
        return pressure;
    }
}
