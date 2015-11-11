package com.andela.checkpoint.onestep.services;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Geocoder;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.controllers.LocationListActivity;
import com.andela.checkpoint.onestep.database.TimePreference;
import com.andela.checkpoint.onestep.models.LocationHelper;

import java.util.Locale;

/**
 * Created by andela-jugba on 11/3/15.
 */
public class TrackerService extends Service {

    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.andela.checkpoint.onestep.receiver";
    private static final String TAG = "TrackerService";
    public static final float STEP_SIZE = 50;
    private Geocoder mGeocoder;
    private LocationHelper mLocationHelper;
    private AddressFinder mAddressFinder;
    private LocationService mLocationService;
    private LocationTimer mLocationTimer;
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

            @Override
            public void onConnected(Location location) {
                mCurrentLocation = location;
            }
        });

        startTracking(true);

        mLocationTimer = new LocationTimer(setTime * 60 * 1000, 1000, mCurrentLocation, new LocationTimer.Callback() {
            @Override
            public void onTick(long l) {
                updatePosition((int) l);
            }

            @Override
            public void onFinish() {
                recordPosition();
            }
        });
        return START_STICKY;
    }

    private void recordPosition() {
        String locationName = mAddressFinder.findLocationAddress(mLocationTimer.mTemporaryLocation);
        com.andela.checkpoint.onestep.models.Location location = new
                com.andela.checkpoint.onestep.models.Location(mLocationTimer.mTemporaryLocation, locationName, (int) mLocationTimer.mStartTime);
        mLocationHelper.addLocation(location);
        sendNotifications();
        mLocationTimer.start();
    }

    private void updatePosition(int l) {
        int a = l / 1000;
        if (mLocationTimer.mTemporaryLocation == null) {
            mLocationTimer.mTemporaryLocation = mCurrentLocation;
        }
        if (a % 10 == 0) {
            mLocationTimer.mDistanceInMeters = mLocationTimer.distanceTo(mCurrentLocation);
            if (mLocationTimer.mDistanceInMeters > STEP_SIZE) {
                mLocationTimer.mTemporaryLocation = mCurrentLocation;
                updateUICounter(true);
                mLocationTimer.cancel();
                mLocationTimer.start();
            }
        }
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
        mLocationTimer.cancel();
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
