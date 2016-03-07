package com.sean.blueskyweather.ui;

import com.sean.blueskyweather.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by snkelley on 1/22/2016. :)
 */
public class AlertDialogFragmentTest {
    private AlertDialogFragment aDFragment;

    @Before
    public void Setup(){
        aDFragment = new AlertDialogFragment();
    }

    @After
    public void Teardown(){
        aDFragment = null;
    }

    @Test
    public void testSetters(){
        aDFragment.setErrorText(R.string.error_with_location);
    }
}
