package com.sean.stormy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sean.stormy.R;
import com.sean.stormy.weather.Current;
import com.sean.stormy.weather.Forecast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class  MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private boolean mResolvingError;
    private double mLatitude;
    private double mLongitude;
    private String mCurrentCity;
    private String mCurrentState;
    private Forecast mForecast;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

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

        //final double latitude = 37.8267;
        //final double longitude = -122.423;

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getForecast(latitude, longitude);
                toggleRefresh();
                getForecast();
            }
        });
        //getForecast(latitude, longitude);
        toggleRefresh();
        buildGoogleApiClient();
    }

    private void getForecast() {
        String apiKey = getString(R.string.API_KEY_DARKSKY);

        String forecastUrl = "https://api.forecast.io/forecast/"+ apiKey + "/"+mLatitude+","+mLongitude;
        if (isNetworkAvailable()) {
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
                            alertUserAboutError(R.string.error_message);
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
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError(R.string.error_message);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }else{
            toggleRefresh();
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
        mLocationLabel.setText(mCurrentCity + ", " + mCurrentState);
        mTemperatureLabel.setText(mForecast.getCurrent().getTemperature() + "");
        mTimeLabel.setText("At " +  mForecast.getCurrent().getFormattedTime() + " it is");
        mHumidityValue.setText(mForecast.getCurrent().getHumidity() + "");
        mPrecipValue.setText(mForecast.getCurrent().getPrecipChance() + "%");
        mSummaryLabel.setText(mForecast.getCurrent().getSummary());
        mIconImageView.setImageDrawable(getResources().getDrawable(mForecast.getCurrent().getIconId()));
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }

    private Forecast parseForecastDetails(String JSONData) throws JSONException {
        Forecast forecast = new Forecast(JSONData);
        return forecast;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject currently = forecast.getJSONObject("currently");
        return new Current(currently, forecast.getString("timezone"));
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

    private void alertUserAboutError(int errorId) {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.setErrorText(errorId);
        dialog.show(getFragmentManager(), getString(R.string.error_dialog_tag));
    }

    private void checkLocation(){
        if (mLastLocation == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }else{
            setLocationInfo();
        }
    }

    private void setLocationInfo() {
        mLatitude = mLastLocation.getLatitude();
        mLongitude = mLastLocation.getLongitude();
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(mLatitude, mLongitude, 1);
            if (addresses.size() > 0) {
                mCurrentCity = addresses.get(0).getLocality();
                mCurrentState = addresses.get(0).getAdminArea();
            }
        }catch (Exception e){
            alertUserAboutError(R.string.error_with_location);
        }
        getForecast();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        checkLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        alertUserAboutError(R.string.connection_suspended);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, 1001);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GooglePlayServicesUtil.getErrorDialog()
            mResolvingError = true;
            alertUserAboutError(R.string.connection_failed);
            mResolvingError = false;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        checkLocation();
    }
}
