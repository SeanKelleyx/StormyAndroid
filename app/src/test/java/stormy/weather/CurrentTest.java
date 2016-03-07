package com.sean.blueskyweather.weather;

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
 * Created by snkelley on 1/20/2016. :)
 */
public class CurrentTest {
    final long time = 1453310735;
    final String summary = "Partly Cloudy";
    final String icon = "partly-cloudy-day";
    final int precipProbability = 0;
    final double temperature = 52.38;
    final double humidity = 0.92;
    final String timezone = "America/Los_Angeles";
    private JSONObject forecast;
    private Current current;

    @Before
    public void Setup() throws JSONException {
        forecast = mock(JSONObject.class);
        when(forecast.getDouble("humidity")).thenReturn(humidity);
        when(forecast.getLong("time")).thenReturn(time);
        when(forecast.getString("icon")).thenReturn(icon);
        when(forecast.getInt("precipProbability")).thenReturn(precipProbability);
        when(forecast.getString("summary")).thenReturn(summary);
        when(forecast.getDouble("temperature")).thenReturn(temperature);
        current = new Current(forecast, timezone);
    }

    @After
    public void Teardown(){
        forecast = null;
        current = null;
    }

    @Test
    public void testConstructor(){
        Assert.assertEquals(current.getFormattedTime(),"9:25 AM");
        Assert.assertEquals(current.getHumidity(), humidity);
        Assert.assertEquals(current.getIcon(), icon);
        Assert.assertEquals(current.getIconId(), R.drawable.partly_cloudy);
        Assert.assertEquals(current.getPrecipChance(),precipProbability);
        Assert.assertEquals(current.getSummary(),summary);
        Assert.assertEquals(current.getTemperature(), 52);
        Assert.assertEquals(current.getTime(), time);
        Assert.assertEquals(current.getTimeZone(), timezone);
    }

    @Test
    public void testGettersSetters(){
        current.setTime(1457280725);
        current.setHumidity(0.12);
        current.setIcon("clear-day");
        current.setPrecipChance(0.13);
        current.setTemperature(99.33);
        current.setSummary("Clear Day");
        current.setTimeZone(timezone);
        Assert.assertEquals(current.getFormattedTime(),"8:12 AM");
        Assert.assertEquals(current.getHumidity(), 0.12);
        Assert.assertEquals(current.getIcon(), "clear-day");
        Assert.assertEquals(current.getIconId(), R.drawable.clear_day);
        Assert.assertEquals(current.getPrecipChance(), 13);
        Assert.assertEquals(current.getSummary(),"Clear Day");
        Assert.assertEquals(current.getTemperature(), 99);
        Assert.assertEquals(current.getTime(), 1457280725);
        Assert.assertEquals(current.getTimeZone(), timezone);
    }

}
