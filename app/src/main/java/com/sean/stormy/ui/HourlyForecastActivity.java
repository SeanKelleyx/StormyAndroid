package com.sean.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import com.sean.stormy.R;
import com.sean.stormy.adapters.HourAdapter;
import com.sean.stormy.weather.Hour;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HourlyForecastActivity extends ListActivity {
    private Hour[] mHours;
    private String mLocationString;

    @InjectView(R.id.locationLabel) TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);

        ButterKnife.inject(this);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);
        mLocationString = intent.getStringExtra(MainActivity.CITY_STATE);

        mLocationLabel.setText(mLocationString);

        HourAdapter adapter = new HourAdapter(this, mHours);
        setListAdapter(adapter);
    }

}
