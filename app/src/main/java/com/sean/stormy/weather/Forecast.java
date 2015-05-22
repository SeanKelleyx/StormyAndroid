package com.sean.stormy.weather;

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

    public Forecast(String JSONData) throws JSONException {
        JSONObject forecast = new JSONObject(JSONData);
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
}
