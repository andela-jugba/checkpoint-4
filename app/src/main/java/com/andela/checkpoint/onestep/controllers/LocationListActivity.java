package com.andela.checkpoint.onestep.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import com.andela.checkpoint.onestep.fragments.BaseFragmentActivity;
import com.andela.checkpoint.onestep.fragments.LocationListFragment;

/**
 * Created by andela-jugba on 11/2/15.
 */
public class LocationListActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LocationListFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
