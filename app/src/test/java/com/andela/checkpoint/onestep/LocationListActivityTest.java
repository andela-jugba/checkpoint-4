package com.andela.checkpoint.onestep;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.andela.checkpoint.onestep.controllers.LocationListActivity;
import com.andela.checkpoint.onestep.controllers.MainActivity;
import com.andela.checkpoint.onestep.fragments.LocationByDateFragment;
import com.andela.checkpoint.onestep.fragments.LocationByLocationFragment;
import com.andela.checkpoint.onestep.fragments.LocationListFragment;
import com.andela.checkpoint.onestep.models.Location;
import com.andela.checkpoint.onestep.models.LocationHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by andela-jugba on 11/6/15.
 */


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LocationListActivityTest {

    private MainActivity testMainActivity;
    private LocationListActivity testLocationListActivity;
    private LocationByDateFragment testLocationByDateFragment;
    private LocationByLocationFragment testLocationByLocationFragment;
    private LocationListFragment testLocationListFragment;
    private FragmentManager testFragmentManager;
    private ViewPager viewPager;
    private LocationHelper testLocationHelper;

    private ShadowActivity shadowActivity;




    @Before
    public void setUp() throws Exception {
        testMainActivity = Robolectric.setupActivity(MainActivity.class);

        testLocationListActivity = Robolectric.setupActivity(LocationListActivity.class);
//        shadowActivity = shadowOf(testLocationListActivity);
        testLocationListFragment = new LocationListFragment();

        testFragmentManager = testLocationListActivity.getSupportFragmentManager();
        testFragmentManager.beginTransaction().add(testLocationListFragment,"Test").commit();
        testLocationListFragment = (LocationListFragment) testFragmentManager.findFragmentById(R.id.fragmentContainer);


        testLocationHelper = LocationHelper.get(RuntimeEnvironment.application);

        Location testLocationOne = new Location();
        testLocationOne.setName("M55");
        testLocationOne.setLatitude(6.777);
        testLocationOne.setLongitude(5.66);
        testLocationOne.setTimesVisited(1);

        Location testLocationTwo = new Location();
        testLocationTwo.setName("Yaba");
        testLocationTwo.setLatitude(6.567);
        testLocationTwo.setLongitude(5.645);
        testLocationTwo.setTimesVisited(1);

        Location testLocationThree = new Location();
        testLocationThree.setName("Amity");
        testLocationThree.setLatitude(7.8);
        testLocationThree.setLongitude(7.88);
        testLocationThree.setTimesVisited(1);

//        testLocationHelper.addLocation(testLocationOne);
//        testLocationHelper.addLocation(testLocationTwo);
//        testLocationHelper.addLocation(testLocationThree);
    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void testActivityExits() throws Exception {
        assertNotNull(testMainActivity);
        assertNotNull(testFragmentManager);
        assertNull(testLocationListFragment);
    }

    @Test
    public void testLocationByDateFragment() throws Exception {


    }
}
