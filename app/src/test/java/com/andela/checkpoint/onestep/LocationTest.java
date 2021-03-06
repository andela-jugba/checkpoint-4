package com.andela.checkpoint.onestep;

import com.andela.checkpoint.onestep.controllers.MainActivity;
import com.andela.checkpoint.onestep.models.Location;
import com.andela.checkpoint.onestep.models.LocationHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        testLocationHelper.addLocation(testLocationOne);
        testLocationHelper.addLocation(testLocationTwo);
        testLocationHelper.addLocation(testLocationThree);

    }

    @After
    public void tearDown() throws Exception {
        testMainActivity = null;
        testLocationHelper.deleteLocation(testLocationOne);
        testLocationHelper.deleteLocation(testLocationTwo);
        testLocationHelper.deleteLocation(testLocationThree);

    }

    @Test
    public void testLocationSavesAndDeletes() throws Exception {
        List<Location> locations;

        HashMap<String, List<Location>> places = testLocationHelper.getLocationByPlaceName();
        assertEquals("Should be 3", places.size(), 3, 0.0);

        for (Map.Entry<String, List<Location>> entry : places.entrySet()) {
            assertEquals(entry.getValue().size(), 1.0, 0.0);
        }

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

        HashMap<String, List<Location>> dates = testLocationHelper.getLocationByDate();
        assertEquals("Should be 1", dates.size(), 1, 0.0);

        testLocationTwo.setDate(new GregorianCalendar(2015, 2, 11).getTime());
        testLocationHelper.updateLocation(testLocationTwo);

        dates = testLocationHelper.getLocationByDate();
        assertEquals("Should be 2", dates.size(), 2, 0.0);


        for (Map.Entry<String, List<Location>> entry : dates.entrySet()) {
            assertEquals(entry.getValue().size(), 1.0, 0.0);
        }

    }


}
