package com.sean.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.sean.stormy.AnalyticsApplication;
import com.sean.stormy.R;
import com.sean.stormy.adapters.HourAdapter;
import com.sean.stormy.weather.Hour;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HourlyForecastActivity extends ListActivity {
    private Hour[] mHours;
    private String mLocationString;
    private Tracker mTracker;

    @InjectView(R.id.locationLabel) TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);

        ButterKnife.inject(this);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Hourly");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);
        mLocationString = intent.getStringExtra(MainActivity.CITY_STATE);

        mLocationLabel.setText(mLocationString);

        HourAdapter adapter = new HourAdapter(this, mHours);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String time = mHours[position].getHour();
        String day = mHours[position].getDay();
        String temperature = mHours[position].getTemperature() + "";
        String summary = mHours[position].getSummary();
        String message = String.format("At %s on %s it will be %s and %s",
                time, day, temperature, summary);
        //TODO make activity to show hourly details
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

}
