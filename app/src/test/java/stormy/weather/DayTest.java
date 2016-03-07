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
 * Created by Sean on 1/20/16. :)
 */
public class DayTest {
    final long time = 1453310735;
    final String summary = "Partly Cloudy";
    final String icon = "partly-cloudy-day";
    final double temperatureMax = 52.38;
    final String timezone = "America/Los_Angeles";
    private JSONObject forecast;
    private Day day;
    private Parcel parcel;

    @Before
    public void Setup() throws JSONException {
        parcel = mock(Parcel.class);
        when(parcel.readLong()).thenReturn(time);
        when(parcel.readString()).thenReturn(summary);
        when(parcel.readDouble()).thenReturn(temperatureMax);
        when(parcel.readString()).thenReturn(icon);
        when(parcel.readString()).thenReturn(timezone);
        forecast = mock(JSONObject.class);
        when(forecast.getLong("time")).thenReturn(time);
        when(forecast.getString("icon")).thenReturn(icon);
        when(forecast.getString("summary")).thenReturn(summary);
        when(forecast.getDouble("temperatureMax")).thenReturn(temperatureMax);
        day = new Day(forecast, timezone);
    }

    @After
    public void Teardown(){
        parcel = null;
        forecast = null;
        day = null;
    }

    @Test
    public void testConstructor(){
        Assert.assertEquals(time, day.getTime());
        Assert.assertEquals(summary, day.getSummary());
        Assert.assertEquals(icon, day.getIcon());
        Assert.assertEquals(R.drawable.partly_cloudy, day.getIconId());
        Assert.assertEquals(52, day.getTemperatureMax());
        Assert.assertEquals(timezone, day.getTimezone());
        Assert.assertEquals("Wednesday", day.getDayOfTheWeek());
    }

    @Test
    public void testGettersSetters(){
        day.setTime(1457280725);
        day.setSummary("Clear Day");
        day.setIcon("clear-day");
        day.setTemperatureMax(99.99);
        day.setTimezone(timezone);
        Assert.assertEquals(1457280725, day.getTime());
        Assert.assertEquals("Clear Day", day.getSummary());
        Assert.assertEquals("clear-day", day.getIcon());
        Assert.assertEquals(R.drawable.clear_day, day.getIconId());
        Assert.assertEquals(100, day.getTemperatureMax());
        Assert.assertEquals(timezone, day.getTimezone());
        Assert.assertEquals("Sunday", day.getDayOfTheWeek());
    }

    @Test
    public void testDescribeContents(){
        Assert.assertEquals(0,day.describeContents());
    }

    @Test
    public void testWriteToParcel(){
        day.writeToParcel(parcel,0 );
    }

    @Test
    public void testCreatorCreateFromParcel() {
        day.writeToParcel(parcel, 0);
        when(parcel.readLong()).thenReturn(time);
        when(parcel.readString()).thenReturn(summary, icon, timezone);
        when(parcel.readDouble()).thenReturn(temperatureMax);
        Day newDay = Day.CREATOR.createFromParcel(parcel);
        Assert.assertEquals(time, newDay.getTime());
        Assert.assertEquals(summary, newDay.getSummary());
        Assert.assertEquals(icon, newDay.getIcon());
        Assert.assertEquals(R.drawable.partly_cloudy, newDay.getIconId());
        Assert.assertEquals(52, newDay.getTemperatureMax());
        Assert.assertEquals(timezone, newDay.getTimezone());
        Assert.assertEquals("Wednesday", newDay.getDayOfTheWeek());
    }

    @Test
    public void testCreatorNewArray(){
        Day[] days = Day.CREATOR.newArray(2);
        Assert.assertEquals(2, days.length);
    }
}
