package com.sean.stormy.weather;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * Created by SeanKelley on  5/21/15.
 */
public class Hour {
    private long mTime;
    private String mSummary;
    private double mTemperature;
    private String mIcon;
    private String mTimezone;

    public Hour(JSONObject JSONData, String timezone) throws JSONException {
        this.setTime(JSONData.getLong("time"));
        this.setSummary(JSONData.getString("summary"));
        this.setTemperature(JSONData.getDouble("temperature"));
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

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
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
