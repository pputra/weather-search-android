package com.example.weatherapp.models;

import android.support.annotation.Nullable;

import com.example.weatherapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weather implements Serializable {
    private String location;
    private Double lat;
    private Double lon;
    private String icon;
    private int temperature;
    private String summary;
    private Double humidity;
    private Double windSpeed;
    private Double visibility;
    private Double pressure;
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

    public Double getHumidity() {
        return humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getVisibility() {
        return visibility;
    }

    public Double getPressure() {
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

    public void setDailyDataList(JSONArray dailyDatalist, long offset) throws JSONException {
        this.dailyDataList = new ArrayList<>();



        for (int i = 0; i < dailyDatalist.length(); i++) {
            JSONObject dailyData = dailyDatalist.getJSONObject(i);
            int time = dailyData.getInt("time");
            String icon = dailyData.getString("icon");
            int minTemperature = (int) Math.round(dailyData.getDouble("temperatureLow"));
            int maxTemperature = (int) Math.round(dailyData.getDouble("temperatureHigh"));

            this.dailyDataList.add(new DailyData(time, offset, icon, minTemperature, maxTemperature));
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

    public static Map<String, Integer> getIconMap() {
        Map<String, Integer> iconMap = new HashMap<>();

        iconMap.put("clear-day", R.drawable.ic_weather_sunny);
        iconMap.put("clear-night", R.drawable.ic_weather_night);
        iconMap.put("rain", R.drawable.ic_weather_rainy);
        iconMap.put("sleet", R.drawable.ic_weather_snowy_rainy);
        iconMap.put("snow", R.drawable.ic_weather_snowy);
        iconMap.put("wind", R.drawable.ic_weather_windy_variant);
        iconMap.put("fog", R.drawable.ic_weather_fog);
        iconMap.put("cloudy", R.drawable.ic_weather_cloudy);
        iconMap.put("partly-cloudy-night", R.drawable.ic_weather_night_partly_cloudy);
        iconMap.put("partly-cloudy-day", R.drawable.ic_weather_partly_cloudy);

        return iconMap;
    }

    public class DailyData implements Serializable {
        private long time;
        private long offset;
        private String icon;
        private int minTemperature;
        private int maxTemperature;

        public DailyData(long time, long offset, String icon, int minTemperature, int maxTemperature) {
            this.time = time;
            this.offset = offset;
            this.icon = icon;
            this.minTemperature = minTemperature;
            this.maxTemperature = maxTemperature;
        }

        public String getTimeInDateFormat() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY");
            Date date = new Date(time * 1000);

            return simpleDateFormat.format(date);
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

        @Override
        public int hashCode() {
            return location.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj == this) return true;

            if (!(obj instanceof Weather)) return false;

            Weather o = (Weather) obj;

            return location.equals(o.location);
        }
    }
}
