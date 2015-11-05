package com.andela.checkpoint.onestep.services;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.controllers.LocationListActivity;
import com.andela.checkpoint.onestep.fragments.LocationByLocationFragment;
import com.andela.checkpoint.onestep.fragments.LocationFragment;
import com.andela.checkpoint.onestep.models.LocationHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by andela-jugba on 11/3/15.
 */
public class TrackerService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private static final String TAG = "TrackerService";

    private Geocoder geocoder;
    private CustomCountDown countDownTimer;
    private LocationHelper locationHelper;

    public static Intent newIntent(Context context) {
        return new Intent(context, TrackerService.class);
    }

    public TrackerService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        startTracking(true);
        countDownTimer = new CustomCountDown(60000, 1000);
        return START_STICKY;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        geocoder = new Geocoder(this, Locale.getDefault());
        locationHelper = LocationHelper.get(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    protected void onHandleIntent(Intent intent) {

        if (!isNetworkAvailableAndConnected()) {
            return;
        }
        Log.i(TAG, "Received an intent: " + intent);
        startTracking(true);
    }

    private void startTracking(boolean start) {

        if (start) {
            mRequestingLocationUpdates = true;

            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
        if (!start) {
            mRequestingLocationUpdates = false;
            Log.i(TAG, "service should be stopped");
            stopLocationUpdates();
            return;
        }

    }


    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        getApplicationContext();
        return isNetworkConnected;
    }

    private String findLocationAddress(Location location) {
        String errorMessage = "";
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, we get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available);
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used);
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " + location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
                showToast("No address found!");

            }
            return "UNKNOWN LOCATION";
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();


            address.getCountryName();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            addressFragments.add(address.getCountryName());


            showToast(TextUtils.join(System.getProperty("line.separator"), addressFragments));
            return TextUtils.join(System.getProperty("line.separator"), addressFragments);
        }
    }

    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000 * 20;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    protected final static String LOCATION_KEY = "location-key";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;


    protected Boolean mRequestingLocationUpdates;

    protected String mLastUpdateTime;


    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");

        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        if (location != null) {
            findLocationAddress(location);
            Toast.makeText(this, "Longitude " + mCurrentLocation.getLongitude() + " Latitude " +
                    mCurrentLocation.getLatitude(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startTracking(false);
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        countDownTimer.cancel();
    }

    public class CustomCountDown extends CountDownTimer {
        private double mStartTime;
        private Location temLocation;
        private float distanceInMeters;

        public CustomCountDown(long startTime, long interval) {
            super(startTime, interval);
            mStartTime = startTime / 1000;
            temLocation = mCurrentLocation;
            this.start();
        }


        @Override
        public void onTick(long l) {
            int a = (int) l / 1000;

            if(a%10 == 0){
                if(temLocation == null){
                    temLocation = mCurrentLocation;
                }
                distanceInMeters = temLocation.distanceTo(mCurrentLocation);
                Toast.makeText(getApplicationContext(), distanceInMeters+"", Toast.LENGTH_LONG).show();
                if(distanceInMeters > 25.0){
                    this.cancel();
                    this.start();
                }
            }
        }

        @Override
        public void onFinish() {
            String locationName = findLocationAddress(temLocation);
            com.andela.checkpoint.onestep.models.Location location = new
                    com.andela.checkpoint.onestep.models.Location(temLocation,locationName, (int) mStartTime);
            locationHelper.addLocation(location);

            // send notifications
            Resources resources = getResources();

            Intent i = LocationFragment.newIntent(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), LocationListActivity.class);
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            Notification notification = new NotificationCompat.Builder(getBaseContext())
                    .setTicker(resources.getString(R.string.new_pictures_title))
                    .setSmallIcon(R.drawable.ic_location_recorded)
                    .setContentTitle(resources.getString(R.string.new_pictures_title))
                    .setContentText(resources.getString(R.string.new_pictures_text))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getBaseContext());
            notificationManager.notify(0, notification);
            this.start();
        }
    }


}
