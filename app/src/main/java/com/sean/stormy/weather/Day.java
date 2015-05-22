package com.sean.stormy.weather;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * Created by snkelley on 5/22/2015.
 */
public class Day {
    private long mTime;
    private String mSummary;
    private double mTemperatureMax;
    private String mIcon;
    private String mTimezone;

    public Day(JSONObject JSONData, String timezone) throws JSONException {
        this.setTime(JSONData.getLong("time"));
        this.setSummary(JSONData.getString("summary"));
        this.setTemperatureMax(JSONData.getDouble("temperatureMax"));
        this.setIcon(JSONData.getString("icon"));
        this.setTimezone(timezone);
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public double getTemperatureMax() {
        return mTemperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }
}
