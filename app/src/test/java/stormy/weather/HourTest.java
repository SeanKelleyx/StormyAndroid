package com.sean.blueskyweather.weather;

import android.os.Parcel;

import com.sean.blueskyweather.R;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by sean kelley on 1/21/2016. :)
 */
public class HourTest {
    final long time = 1453310735;
    final String summary = "Partly Cloudy";
    final String icon = "partly-cloudy-day";
    final double temperature = 52.38;
    final String timezone = "America/Los_Angeles";
    private JSONObject forecast;
    private Hour hour;
    private Parcel parcel;

    @Before
    public void Setup() throws JSONException {
        forecast = mock(JSONObject.class);
        parcel = mock(Parcel.class);
        when(forecast.getLong("time")).thenReturn(time);
        when(forecast.getString("icon")).thenReturn(icon);
        when(forecast.getString("summary")).thenReturn(summary);
        when(forecast.getDouble("temperature")).thenReturn(temperature);
        hour = new Hour(forecast, timezone);
    }

    @After
    public void Teardown(){
        forecast = null;
        hour = null;
        parcel = null;
    }

    @Test
    public void testConstructor(){
        Assert.assertEquals(hour.getTemperature(),52);
        Assert.assertEquals(hour.getTime(),time);
        Assert.assertEquals(hour.getHour(), "9 AM");
        Assert.assertEquals(hour.getSummary(),summary);
        Assert.assertEquals(hour.getIcon(), icon);
        Assert.assertEquals(hour.getIconId(), R.drawable.partly_cloudy);
        Assert.assertEquals(hour.getTimezone(), timezone);
        Assert.assertEquals(hour.getDay(), "Wednesday");
        Assert.assertEquals(hour.getDayAbbr(), "Wed");
    }

    @Test
    public void testGettersSetters(){
        hour.setTime(1457280725);
        hour.setIcon("clear-day");
        hour.setSummary("Clear Day");
        hour.setTemperature(99.99);
        hour.setTimezone(timezone);
        Assert.assertEquals(hour.getTemperature(), 100);
        Assert.assertEquals(hour.getTime(),1457280725);
        Assert.assertEquals(hour.getHour(), "8 AM");
        Assert.assertEquals(hour.getSummary(),"Clear Day");
        Assert.assertEquals(hour.getIcon(), "clear-day");
        Assert.assertEquals(hour.getIconId(), R.drawable.clear_day);
        Assert.assertEquals(hour.getTimezone(), timezone);
        Assert.assertEquals(hour.getDay(), "Sunday");
        Assert.assertEquals(hour.getDayAbbr(), "Sun");
    }

    @Test
    public void testDescribeContents(){
        Assert.assertEquals(0,hour.describeContents());
    }

    @Test
    public void testWriteToParcel() {
        hour.writeToParcel(parcel, 0);
    }

    @Test
    public void testCreatorCreateFromParcel() {
        hour.writeToParcel(parcel, 0);
        when(parcel.readLong()).thenReturn(time);
        when(parcel.readString()).thenReturn(summary,icon,timezone);
        when(parcel.readDouble()).thenReturn(temperature);
        Hour newHour = Hour.CREATOR.createFromParcel(parcel);
        Assert.assertEquals(newHour.getTemperature(),52);
        Assert.assertEquals(newHour.getTime(), time);
        Assert.assertEquals(newHour.getHour(), "9 AM");
        Assert.assertEquals(newHour.getSummary(),summary);
        Assert.assertEquals(newHour.getIcon(), icon);
        Assert.assertEquals(newHour.getIconId(), R.drawable.partly_cloudy);
        Assert.assertEquals(newHour.getTimezone(), timezone);
        Assert.assertEquals(newHour.getDay(), "Wednesday");
        Assert.assertEquals(newHour.getDayAbbr(), "Wed");
    }

    @Test
    public void testCreatorNewArray(){
        Hour[] hours = Hour.CREATOR.newArray(2);
        Assert.assertEquals(2,hours.length);
    }
}
