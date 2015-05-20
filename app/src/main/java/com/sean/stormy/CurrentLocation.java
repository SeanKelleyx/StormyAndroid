package com.sean.stormy;
/**
 * Created by snkelley on 5/20/2015.
 */
public class CurrentLocation {
    private double mLatitude;
    private double mLongitude;
    private String mState;
    private String mCity;
    public CurrentLocation (double lat, double lon, String state, String city){
        this.mLatitude = lat;
        this.mLongitude = lon;
        this.mState = state;
        this.mCity = city;
    }
    public double getLatitude() {
        return mLatitude;
    }
    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }
    public double getLongitude() {
        return mLongitude;
    }
    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
    public String getState() {
        return mState;
    }
    public void setState(String state) {
        mState = state;
    }
    public String getCity() {
        return mCity;
    }
    public void setCity(String city) {
        mCity = city;
    }
}
