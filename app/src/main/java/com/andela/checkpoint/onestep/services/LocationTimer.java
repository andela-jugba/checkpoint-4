package com.andela.checkpoint.onestep.services;

import android.location.Location;
import android.os.CountDownTimer;

/**
 * Created by andela-jugba on 11/9/15.
 */
public class LocationTimer extends CountDownTimer {

    protected double mStartTime;
    protected Location mTemporaryLocation;
    protected float mDistanceInMeters;
    private Callback mCallback;

    public LocationTimer(long millisInFuture, long countDownInterval, Location location, Callback callback) {
        super(millisInFuture, countDownInterval);
        mCallback = callback;
        mStartTime = millisInFuture / (1000 * 60);
        mTemporaryLocation = location;
        this.start();
    }

    @Override
    public void onTick(long l) {
        mCallback.onTick(l);
    }

    @Override
    public void onFinish() {
        mCallback.onFinish();
    }

    public float distanceTo(Location location){
        return mTemporaryLocation.distanceTo(location);
    }

    interface Callback {
        void onTick(long l);

        void onFinish();
    }
}
