package com.andela.checkpoint.onestep;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.andela.checkpoint.onestep.controllers.LocationListActivity;
import com.andela.checkpoint.onestep.fragments.LocationByDateFragment;
import com.andela.checkpoint.onestep.fragments.LocationByLocationFragment;
import com.andela.checkpoint.onestep.fragments.LocationListFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

/**
 * Created by andela-jugba on 11/6/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LocationListActivityTest {

    private LocationListActivity testLocationListActivity;
    private LocationByDateFragment testLocationByDateFragment;
    private LocationByLocationFragment testLocationByLocationFragment;
    private LocationListFragment testLocationListFragment;
    private FragmentManager testFragmentManager;
    private ViewPager viewPager;

    @Before
    public void setUp() throws Exception {
        testLocationListActivity = Robolectric.setupActivity(LocationListActivity.class);
        testFragmentManager = testLocationListActivity.getSupportFragmentManager();
        testLocationListFragment = (LocationListFragment) testFragmentManager.findFragmentById(R.id.fragmentContainer);

    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void testActivityExits() throws Exception {
        assertNotNull(testLocationListActivity);
        assertNotNull(testFragmentManager);
        assertNotNull(testLocationListFragment);
    }

    @Test
    public void testLocationByDateFragment() throws Exception {


    }
}
