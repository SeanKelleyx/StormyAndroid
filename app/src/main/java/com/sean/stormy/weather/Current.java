package com.sean.stormy.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/*
 * Created by snkelley on 4/23/2015.
 */
public class Current {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mPrecipChance;
    private double mHumidity;
    private String mSummary;
    private String mTimeZone;

    public Current(JSONObject currently, String timezone) throws JSONException {
        this.setHumidity(currently.getDouble("humidity"));
        this.setTime(currently.getLong("time"));
        this.setIcon(currently.getString("icon"));
        this.setPrecipChance(currently.getDouble("precipProbability"));
        this.setSummary(currently.getString("summary"));
        this.setTemperature(currently.getDouble("temperature"));
        this.setTimeZone(timezone);
    }

    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        long dateTime = System.currentTimeMillis();
        return formatter.format(dateTime);
    }

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {
        return (int)Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public int getPrecipChance() {
        double precipPercentage = mPrecipChance * 100;
        return (int) Math.round(precipPercentage);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public String getSummary() { return mSummary;  }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTimeZone() { return mTimeZone; }

    public void setTimeZone(String mTimeZone) { this.mTimeZone = mTimeZone; }

}
