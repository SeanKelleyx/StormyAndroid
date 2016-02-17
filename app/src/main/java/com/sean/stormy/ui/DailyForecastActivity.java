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
import com.sean.stormy.adapters.DayAdapter;
import com.sean.stormy.weather.Day;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DailyForecastActivity extends ListActivity {
    private Day[] mDays;
    private String mLocationString;
    private Tracker mTracker;

    @InjectView(R.id.locationLabel) TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        ButterKnife.inject(this);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Daily");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        mLocationString = intent.getStringExtra(MainActivity.CITY_STATE);

        mLocationLabel.setText(mLocationString);

        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String dayOfTheWeek = mDays[position].getDayOfTheWeek();
        String conditions = mDays[position].getSummary();
        String highTemp = mDays[position].getTemperatureMax() + "";
        String message = String.format("On %s the high will be %s and it will be %s",
                dayOfTheWeek, highTemp, conditions);
        //TODO make activity to show daily details
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}
