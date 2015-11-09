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
import com.andela.checkpoint.onestep.database.TimePreference;
import com.andela.checkpoint.onestep.fragments.LocationFragment;
import com.andela.checkpoint.onestep.models.LocationHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by andela-jugba on 11/3/15.
 */
public class TrackerService extends Service {

    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.andela.checkpoint.onestep.receiver";
    private static final String TAG = "TrackerService";
    public static final double STEP_SIZE = 50;
    private Geocoder mGeocoder;
    private CustomCountDown mCountDownTimer;
    private LocationHelper mLocationHelper;
    private AddressFinder mAddressFinder;
    private LocationService mLocationService;

    protected Location mCurrentLocation;


    public static Intent newIntent(Context context) {
        return new Intent(context, TrackerService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int setTime = TimePreference.getStoredTime(getApplicationContext());
        mLocationService = new LocationService(getApplicationContext(), new LocationService.CallBack() {
            @Override
            public void onStepChanged(Location location) {
                mCurrentLocation = location;
            }
        });
        startTracking(true);
        mCountDownTimer = new CustomCountDown(setTime * 60 * 1000, 1000);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGeocoder = new Geocoder(this, Locale.getDefault());
        mAddressFinder = new AddressFinder(mGeocoder);
        mLocationHelper = LocationHelper.get(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void startTracking(boolean start) {

        if (start) {
            mLocationService.mRequestingLocationUpdates = true;
            mLocationService.buildGoogleApiClient();
            mLocationService.connect();
        }
        if (!start) {
            mLocationService.mRequestingLocationUpdates = false;
            mLocationService.stopLocationUpdates();
            Log.i(TAG, "service should be stopped");
            return;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startTracking(false);
        mCountDownTimer.cancel();
    }

    private class CustomCountDown extends CountDownTimer {
        private double mStartTime;
        private Location temLocation;
        private float distanceInMeters;

        public CustomCountDown(long startTime, long interval) {
            super(startTime, interval);
            mStartTime = startTime / (1000 * 60);
            temLocation = mCurrentLocation;
            this.start();
        }

        @Override
        public void onTick(long l) {
            int a = (int) l / 1000;
            if (temLocation == null) {
                temLocation = mCurrentLocation;
            }
            if (a % 10 == 0) {

                distanceInMeters = temLocation.distanceTo(mCurrentLocation);
                if (distanceInMeters > STEP_SIZE) {
                    temLocation = mCurrentLocation;
                    updateUICounter(true);
                    this.cancel();
                    this.start();
                }
            }
        }

        @Override
        public void onFinish() {
            String locationName = mAddressFinder.findLocationAddress(temLocation);
            com.andela.checkpoint.onestep.models.Location location = new
                    com.andela.checkpoint.onestep.models.Location(temLocation, locationName, (int) mStartTime);
            mLocationHelper.addLocation(location);

            // send notifications
            sendNotifications();
            this.start();
        }
    }

    private void sendNotifications() {
        Resources resources = getResources();

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
    }

    private void updateUICounter(boolean result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

}
