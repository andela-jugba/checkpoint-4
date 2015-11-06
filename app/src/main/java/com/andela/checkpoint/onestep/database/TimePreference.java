package com.andela.checkpoint.onestep.database;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by andela-jugba on 11/5/15.
 */
public class TimePreference {
    private static final String PREF_MINIMUM_TIME = "minimumTime";

    public static Integer getStoredTime(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_MINIMUM_TIME, 5);
    }

    public static void setStoredTime(Context context, Integer time) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_MINIMUM_TIME, time)
                .apply();
    }

}
