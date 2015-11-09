package com.andela.checkpoint.onestep;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.andela.checkpoint.onestep.controllers.MainActivity;
import com.andela.checkpoint.onestep.services.TrackerService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by andela-jugba on 10/30/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {
    private MainActivity testMainActivity;
    private Context context;
    private TrackerService trackerService;
    private ShadowActivity shadow;


    @Before
    public void setUp() throws Exception {
        testMainActivity = Robolectric.setupActivity(MainActivity.class);
//        trackerService = Robolectric.buildService(TrackerService.class).get();
//        shadow = shadowOf(testMainActivity);

    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void testMainActivityExits() throws Exception {
        assertNotNull(testMainActivity);

    }

    @Test
    public void testBasicUiElements() throws Exception {

        //given that I am on the main screen of the app
        thenIShouldSeeTheBasicUiElementsInPosition();
        //when I click on Start,
        thenTheSetStepButtonShouldNotBeEnabled();
        thenTheTextShouldChangeToStop();


    }

    private void thenTheTextShouldChangeToStop() {
        TextView testStartView = (TextView) testMainActivity.findViewById(R.id.textViewPlay);
        assertEquals("The text should be equal", testStartView.getText(), "stop");
    }

    private void thenTheSetStepButtonShouldNotBeEnabled() {
        TextView testStartView = (TextView) testMainActivity.findViewById(R.id.textViewPlay);
        testStartView.performClick();
//
//        Intent intent = shadow.getNextStartedService();
//        assertEquals(TrackerService.class.getCanonicalName(), intent.getComponent().getClassName());

        Button testSetButton = (Button) testMainActivity.findViewById(R.id.buttonSetStep);
        assertTrue(!testSetButton.isEnabled());


    }

    private void thenIShouldSeeTheBasicUiElementsInPosition() {
        Button testSetButton = (Button) testMainActivity.findViewById(R.id.buttonSetStep);
        assertNotNull("This should be there when the app loads", testSetButton);
        assertEquals("Text of the button", testSetButton.getText(), "SET STEP");

        Button testLogButton = (Button) testMainActivity.findViewById(R.id.buttonLog);
        assertNotNull("This should be there when the app loads up", testLogButton);
        assertEquals("The Text of the button should be", testLogButton.getText(), "LOGS");

        TextView testStartView = (TextView) testMainActivity.findViewById(R.id.textViewPlay);
        assertNotNull("This be loaded up", testStartView);
        assertEquals("The text should be equal", testStartView.getText(), "start");

        TextView testSecView = (TextView) testMainActivity.findViewById(R.id.textViewSec);
        assertNotNull("This view should be loaded", testSecView);
        assertEquals("The text should be equal", testSecView.getText(), "secs");

    }

}
