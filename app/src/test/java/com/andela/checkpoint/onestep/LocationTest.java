package com.andela.checkpoint.onestep;

import com.andela.checkpoint.onestep.models.Location;
import com.andela.checkpoint.onestep.models.LocationHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by andela-jugba on 11/2/15.
 */


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LocationTest {
    private MainActivity testMainActivity;
    private Location testLocationOne;
    private Location testLocationTwo;
    private Location testLocationThree;

    private LocationHelper testLocationHelper;

    @Before
    public void setUp() throws Exception {
        testMainActivity = Robolectric.setupActivity(MainActivity.class);
        testLocationHelper = LocationHelper.get(testMainActivity);

        testLocationOne = new Location();
        testLocationOne.setName("M55");
        testLocationOne.setLatitude(6.777);
        testLocationOne.setLongitude(5.66);
        testLocationOne.setTimesVisited(1);

        testLocationTwo = new Location();
        testLocationTwo.setName("Yaba");
        testLocationTwo.setLatitude(6.567);
        testLocationTwo.setLongitude(5.645);
        testLocationTwo.setTimesVisited(1);

        testLocationThree = new Location();
        testLocationThree.setName("Amity");
        testLocationThree.setLatitude(7.8);
        testLocationThree.setLongitude(7.88);
        testLocationThree.setTimesVisited(1);


    }

    @After
    public void tearDown() throws Exception {
        testMainActivity = null;

    }

    @Test
    public void testLocationSavesAndDeletes() throws Exception {
        List<Location> locations = testLocationHelper.getLocations();
        assertNull("Should be null", locations);

        testLocationHelper.addLocation(testLocationOne);
        testLocationHelper.addLocation(testLocationTwo);
        testLocationHelper.addLocation(testLocationThree);

        locations = testLocationHelper.getLocations();
        assertEquals("Should be three", locations.size(), 3, 0.0);

        Location temp = testLocationHelper.getLocation(testLocationOne.getID());
        assertNotNull(temp);
        assertEquals("Should have the same ID", testLocationOne.getID(), temp.getID());

        testLocationHelper.deleteLocation(testLocationOne);
        temp = testLocationHelper.getLocation(testLocationOne.getID());
        assertNull("Should return null", temp);

        locations = testLocationHelper.getLocations();
        assertEquals("Should be 2", locations.size(), 2, 0.0);

    }

}
