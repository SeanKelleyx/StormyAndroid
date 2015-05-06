package com.sean.stormy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private CurrentWeather mCurrentWeather;
    @InjectView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @InjectView(R.id.timeLabel) TextView mTimeLabel;
    @InjectView(R.id.humidityValue) TextView mHumidityValue;
    @InjectView(R.id.precipValue) TextView mPrecipValue;
    @InjectView(R.id.summaryLabel) TextView mSummaryLabel;
    @InjectView(R.id.iconImageView) ImageView mIconImageView;
    @InjectView(R.id.refreshImageView) ImageView mRefreshImageView;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;

    @InjectView(R.id.degreeImageView) ImageView mDegreeImageView;
    @InjectView(R.id.humidityLabel) TextView mHumidityLabel;
    @InjectView(R.id.precipLabel) TextView mPrecipLabel;
    @InjectView(R.id.locationLabel) TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        final double latitude = 37.8267;
        final double longitude = -122.423;

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        });
        getForecast(latitude, longitude);
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "4a7a86784dd76767baf4435021887aa1";

        String forecastUrl = "https://api.forecast.io/forecast/"+ apiKey + "/"+latitude+","+longitude;
        if (isNetworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                            alertUserAboutError();
                        }
                    });
                    Log.v(TAG, e.getMessage());
                }

                @Override
                public void onResponse(Response response){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }else{
            Toast.makeText(this, getString(R.string.network_unavailable_message), Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if(mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        toggleView();
        mTemperatureLabel.setText(mCurrentWeather.getTemperature() + "");
        mTimeLabel.setText("At " +  mCurrentWeather.getFormattedTime() + " it is");
        mHumidityValue.setText(mCurrentWeather.getHumidity() + "");
        mPrecipValue.setText(mCurrentWeather.getPrecipChance() + "%");
        mSummaryLabel.setText(mCurrentWeather.getSummary());
        mIconImageView.setImageDrawable(getResources().getDrawable(mCurrentWeather.getIconId()));
        toggleView();
    }

    private void toggleView() {
        if(mTemperatureLabel.getVisibility() == View.VISIBLE) {
            mTemperatureLabel.setVisibility(View.INVISIBLE);
            mTimeLabel.setVisibility(View.INVISIBLE);
            mHumidityValue.setVisibility(View.INVISIBLE);
            mPrecipValue.setVisibility(View.INVISIBLE);
            mSummaryLabel.setVisibility(View.INVISIBLE);
            mIconImageView.setVisibility(View.INVISIBLE);

            mDegreeImageView.setVisibility(View.INVISIBLE);
            mLocationLabel.setVisibility(View.INVISIBLE);
            mHumidityLabel.setVisibility(View.INVISIBLE);
            mPrecipLabel.setVisibility(View.INVISIBLE);
        } else {
            mTemperatureLabel.setVisibility(View.VISIBLE);
            mTimeLabel.setVisibility(View.VISIBLE);
            mHumidityValue.setVisibility(View.VISIBLE);
            mPrecipValue.setVisibility(View.VISIBLE);
            mSummaryLabel.setVisibility(View.VISIBLE);
            mIconImageView.setVisibility(View.VISIBLE);

            mDegreeImageView.setVisibility(View.VISIBLE);
            mLocationLabel.setVisibility(View.VISIBLE);
            mHumidityLabel.setVisibility(View.VISIBLE);
            mPrecipLabel.setVisibility(View.VISIBLE);
        }
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject currently = forecast.getJSONObject("currently");
        return new CurrentWeather(currently, forecast.getString("timezone"));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), getString(R.string.error_dialog_tag));
    }
}
