package com.sean.stormy;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CurrentLocationTest {
    private CurrentLocation currentLoc;
    final Double lat = 10.10;
    final Double lon = 20.20;
    final String state = "WI";
    final String city = "Milwaukee";

    @Before
    public void Setup() {
        currentLoc = new CurrentLocation(lat, lon, state, city);
    }

    @After
    public void Teardown(){
        currentLoc = null;
    }

    @Test
    public void testConstructor() {
        Assert.assertEquals(currentLoc.getLatitude(), lat);
        Assert.assertEquals(currentLoc.getLongitude(), lon);
        Assert.assertEquals(currentLoc.getState(), state);
        Assert.assertEquals(currentLoc.getCity(), city);
    }

    @Test
    public void testGettersSetters(){
        currentLoc.setLatitude(12.34);
        currentLoc.setLongitude(56.78);
        currentLoc.setState("CO");
        currentLoc.setCity("Denver");
        Assert.assertEquals(currentLoc.getLatitude(), 12.34);
        Assert.assertEquals(currentLoc.getLongitude(), 56.78);
        Assert.assertEquals(currentLoc.getState(), "CO");
        Assert.assertEquals(currentLoc.getCity(), "Denver");
    }
}