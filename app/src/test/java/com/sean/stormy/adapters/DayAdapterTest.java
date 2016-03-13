package com.sean.stormy.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.sean.stormy.weather.Day;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by Sean on 1/21/16. :)
 */
public class DayAdapterTest {
    private DayAdapter dayAdapter;
    private Day day;
    private Day[] days;
    private Context context;
    private View view;
    private ViewGroup viewGroup;

    @Before
    public void Setup(){
        view = mock(View.class);
        viewGroup = mock(ViewGroup.class);
        day = mock(Day.class);
        days = new Day[1];
        days[0] = day;
        context = mock(Context.class);
        dayAdapter = new DayAdapter(context,days);
    }

    @After
    public void Teardown(){
        view = null;
        viewGroup = null;
        day = null;
        days = null;
        context = null;
        dayAdapter = null;
    }

    @Test
    public void testConstructor(){
        Assert.assertEquals(1, dayAdapter.getCount());
        Assert.assertEquals(days[0], dayAdapter.getItem(0));
        Assert.assertEquals(0, dayAdapter.getItemId(0));
    }
}
