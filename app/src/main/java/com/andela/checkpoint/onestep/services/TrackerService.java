package com.andela.checkpoint.onestep.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by andela-jugba on 11/3/15.
 */
public class TrackerService extends IntentService {
    private static final String TAG = "TrackerService";

    public static Intent newIntent(Context context) {
        return new Intent(context, TrackerService.class);
    }

    public TrackerService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }
        Log.i(TAG, "Received an intent: " + intent);
    }


    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        getApplicationContext();
        return isNetworkConnected;
    }
}
