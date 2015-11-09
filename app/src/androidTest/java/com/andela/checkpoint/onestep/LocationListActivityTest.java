package com.andela.checkpoint.onestep;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.andela.checkpoint.onestep.controllers.LocationListActivity;
import com.andela.checkpoint.onestep.fragments.LocationByDateFragment;
import com.andela.checkpoint.onestep.fragments.LocationByLocationFragment;
import com.andela.checkpoint.onestep.fragments.LocationListFragment;

/**
 * Created by andela-jugba on 11/6/15.
 */
public class LocationListActivityTest extends ActivityInstrumentationTestCase2 {
    Activity activity;
    private LocationListActivity testLocationListActivity;
    private LocationByDateFragment testLocationByDateFragment;
    private LocationByLocationFragment testLocationByLocationFragment;
    private LocationListFragment testLocationListFragment;
    private FragmentManager testFragmentManager;
    private ViewPager viewPager;

    public LocationListActivityTest(Class activityClass) {
        super(LocationListActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        testLocationListActivity= (LocationListActivity) getActivity();
        testFragmentManager = testLocationListActivity.getSupportFragmentManager();
        testLocationListFragment = (LocationListFragment) testFragmentManager.findFragmentById(R.id.fragmentContainer);
    }


    @SmallTest
    public void testActivityExits() throws Exception {
        assertNotNull(testLocationListActivity);
        assertNotNull(testFragmentManager);
        assertNotNull(testLocationListFragment);
    }
}
