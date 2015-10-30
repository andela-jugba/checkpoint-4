package com.andela.checkpoint.onestep;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

/**
 * Created by andela-jugba on 10/30/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {
    private MainActivity testMainActivity;

    @Before
    public void setUp() throws Exception {
        testMainActivity = Robolectric.setupActivity(MainActivity.class);

    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void testMainActivityExits() throws Exception {
        assertNotNull(testMainActivity);

    }
}
