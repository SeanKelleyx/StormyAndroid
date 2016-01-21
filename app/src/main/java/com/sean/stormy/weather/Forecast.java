package com.sean.stormy.weather;

import com.sean.stormy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Created by snkelley on 5/22/2015.
 */
public class Forecast {
    private Current mCurrent;
    private Hour[] mHourlyForecast;
    private Day[] mDailyForecast;

    public Forecast(JSONObject forecast) throws JSONException {
        JSONObject currently = forecast.getJSONObject("currently");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray hourlyData = hourly.getJSONArray("data");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray dailyData = daily.getJSONArray("data");
        String timezone = forecast.getString("timezone");
        this.setCurrent(new Current(currently, timezone));
        Hour[] hourlyArray = new Hour[hourlyData.length()];
        for(int i = 0; i < hourlyData.length(); i++){
            JSONObject hour = hourlyData.getJSONObject(i);
            hourlyArray[i] = new Hour(hour, timezone);
        }
        this.setHourlyForecast(hourlyArray);
        Day[] dailyArray = new Day[dailyData.length()];
        for(int i = 0; i < dailyData.length(); i++){
            JSONObject day = dailyData.getJSONObject(i);
            dailyArray[i] = new Day(day, timezone);
        }
        this.setDailyForecast(dailyArray);
    }

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }

    public static int getIconId(String iconString){
        int iconId = R.drawable.clear_day;
        if (iconString.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (iconString.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (iconString.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (iconString.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (iconString.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (iconString.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (iconString.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (iconString.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (iconString.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (iconString.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }
        return iconId;
    }
}
