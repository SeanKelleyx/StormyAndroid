package com.sean.blueskyweather.weather;

import com.sean.blueskyweather.R;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by snkelley on 1/21/2016. :)
 */
public class ForecastTest {
    private Forecast forecast;
    private JSONObject forecastJson;
    private JSONObject currently;
    private JSONObject hourly;
    private JSONObject hour1;
    private JSONArray hourlyData;
    private JSONObject daily;
    private JSONObject day1;
    private JSONArray dailyData;
    final long time = 1453310735;
    final String summary = "Partly Cloudy";
    final String icon = "partly-cloudy-day";
    final double temperature = 52.38;
    final private String timezone = "America/Los_Angeles";

    @Before
    public void Setup() throws JSONException {
        currently = mock(JSONObject.class);
        hourly = mock(JSONObject.class);
        hour1 = mock(JSONObject.class);
        hourlyData = mock(JSONArray.class);
        daily = mock(JSONObject.class);
        day1 = mock(JSONObject.class);
        dailyData = mock(JSONArray.class);
        forecastJson = mock(JSONObject.class);
        when(day1.getLong("time")).thenReturn(time);
        when(day1.getString("icon")).thenReturn(icon);
        when(day1.getString("summary")).thenReturn(summary);
        when(day1.getDouble("temperatureMax")).thenReturn(temperature);
        when(hour1.getLong("time")).thenReturn(time);
        when(hour1.getString("icon")).thenReturn(icon);
        when(hour1.getString("summary")).thenReturn(summary);
        when(hour1.getDouble("temperature")).thenReturn(temperature);
        when(forecastJson.getString("timezone")).thenReturn(timezone);
        when(forecastJson.getJSONObject("currently")).thenReturn(currently);
        when(forecastJson.getJSONObject("hourly")).thenReturn(hourly);
        when(hourly.getJSONArray("data")).thenReturn(hourlyData);
        when(hourlyData.length()).thenReturn(1);
        when(hourlyData.getJSONObject(0)).thenReturn(hour1);
        when(forecastJson.getJSONObject("daily")).thenReturn(daily);
        when(daily.getJSONArray("data")).thenReturn(dailyData);
        when(dailyData.length()).thenReturn(1);
        when(dailyData.getJSONObject(0)).thenReturn(day1);
        forecast = new Forecast(forecastJson);
    }

    @After
    public void Teardown(){
        currently = null;
        hourly = null;
        hour1 = null;
        hourlyData = null;
        daily = null;
        day1 = null;
        dailyData = null;
        forecastJson = null;
        forecast = null;
    }

    @Test
    public void testConstructor() throws JSONException {
        Assert.assertEquals(Current.class, forecast.getCurrent().getClass());
        Assert.assertEquals(1,forecast.getHourlyForecast().length);
        Assert.assertEquals(1, forecast.getDailyForecast().length);
    }

    @Test
    public void testGetIconId(){
        Assert.assertEquals(R.drawable.clear_day,Forecast.getIconId("anything_not_otherwise_listed"));
        Assert.assertEquals(R.drawable.clear_day,Forecast.getIconId("clear-day"));
        Assert.assertEquals(R.drawable.clear_night,Forecast.getIconId("clear-night"));
        Assert.assertEquals(R.drawable.rain,Forecast.getIconId("rain"));
        Assert.assertEquals(R.drawable.snow,Forecast.getIconId("snow"));
        Assert.assertEquals(R.drawable.sleet,Forecast.getIconId("sleet"));
        Assert.assertEquals(R.drawable.wind,Forecast.getIconId("wind"));
        Assert.assertEquals(R.drawable.fog,Forecast.getIconId("fog"));
        Assert.assertEquals(R.drawable.cloudy,Forecast.getIconId("cloudy"));
        Assert.assertEquals(R.drawable.partly_cloudy,Forecast.getIconId("partly-cloudy-day"));
        Assert.assertEquals(R.drawable.cloudy_night,Forecast.getIconId("partly-cloudy-night"));
    }
}