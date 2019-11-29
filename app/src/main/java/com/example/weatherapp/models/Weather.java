package com.example.weatherapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Weather implements Serializable {
    private String location;
    private double lat;
    private double lon;
    private String icon;
    private int temperature;
    private String summary;
    private double humidity;
    private double windSpeed;
    private double visibility;
    private double pressure;
    List<DailyData> dailyDataList;

    public Weather(String location, double lat, double lon) {
        this.location = location;
        this.lat = lat;
        this.lon = lon;
    }

    public Weather(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setDailyDataList(JSONArray dailyDatalist) throws JSONException {
        this.dailyDataList = new ArrayList<>();



        for (int i = 0; i < dailyDatalist.length(); i++) {
            JSONObject dailyData = dailyDatalist.getJSONObject(i);
            int time = dailyData.getInt("time");
            String icon = dailyData.getString("icon");
            int minTemperature = (int) Math.round(dailyData.getDouble("temperatureLow"));
            int maxTemperature = (int) Math.round(dailyData.getDouble("temperatureHigh"));

            this.dailyDataList.add(new DailyData(time, icon, minTemperature, maxTemperature));
        }
    }

    public List<DailyData> getDailyDataList() {
        return dailyDataList;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "location='" + location + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", icon='" + icon + '\'' +
                ", temperature=" + temperature +
                ", summary='" + summary + '\'' +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", visibility=" + visibility +
                ", pressure=" + pressure +
                ", dailyDataList=" + dailyDataList +
                '}';
    }

    public class DailyData implements Serializable {
        private int time;
        private String icon;
        private int minTemperature;
        private int maxTemperature;

        public DailyData(int time, String icon, int minTemperature, int maxTemperature) {
            this.time = time;
            this.icon = icon;
            this.minTemperature = minTemperature;
            this.maxTemperature = maxTemperature;
        }

        public int getTime() {
            return time;
        }

        public String getIcon() {
            return icon;
        }

        public int getMinTemperature() {
            return minTemperature;
        }

        public int getMaxTemperature() {
            return maxTemperature;
        }

        @Override
        public String toString() {
            return "DailyData{" +
                    "time=" + time +
                    ", icon='" + icon + '\'' +
                    ", minTemperature=" + minTemperature +
                    ", maxTemperature=" + maxTemperature +
                    '}';
        }
    }
}
