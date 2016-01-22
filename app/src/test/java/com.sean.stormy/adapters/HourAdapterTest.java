package com.sean.stormy.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.sean.stormy.weather.Day;
import com.sean.stormy.weather.Hour;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by Sean on 1/21/16. :)
 */
public class HourAdapterTest {
    private HourAdapter hourAdapter;
    private Hour hour;
    private Hour[] hours;
    private Context context;
    private View view;
    private ViewGroup viewGroup;

    @Before
    public void Setup(){
        view = mock(View.class);
        viewGroup = mock(ViewGroup.class);
        hour = mock(Hour.class);
        hours = new Hour[1];
        hours[0] = hour;
        context = mock(Context.class);
        hourAdapter = new HourAdapter(context,hours);
    }

    @After
    public void Teardown(){
        view = null;
        viewGroup = null;
        hour = null;
        hours = null;
        context = null;
        hourAdapter = null;
    }

    @Test
    public void testConstructor(){
        Assert.assertEquals(1, hourAdapter.getCount());
        Assert.assertEquals(hours[0], hourAdapter.getItem(0));
        Assert.assertEquals(0, hourAdapter.getItemId(0));
    }
}
