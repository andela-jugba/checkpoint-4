package com.andela.checkpoint.onestep;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


import com.andela.checkpoint.onestep.controllers.LocationListActivity;
import com.andela.checkpoint.onestep.models.LocationHelper;
import com.andela.checkpoint.onestep.services.TrackerService;
import com.andressantibanez.ranger.Ranger;
import com.dlazaro66.wheelindicatorview.WheelIndicatorItem;
import com.dlazaro66.wheelindicatorview.WheelIndicatorView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName() ;
    private WheelIndicatorView wheelIndicatorView;
    private CountDownTimer countDownTimer;
    private Button mButtonSetStep;
    private Button buttonPlay;
    private Button mbuttonLogs;
    private TextView textViewStart;
    private Ranger ranger;

    private View hiddenView;
    private TextView mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setUpBasicUI();
        LocationHelper locationHelper = LocationHelper.get(this);

    }

    private void setUpBasicUI() {
        setUpWheelIndicator();
        setUpCounter();

        mbuttonLogs = (Button) findViewById(R.id.buttonLog);
        mbuttonLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        mButtonSetStep = (Button) findViewById(R.id.buttonSetStep);
        hiddenView = findViewById(R.id.hiddenView);

        mButtonSetStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenView.setVisibility(View.VISIBLE);

            }
        });


        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setVisibility(View.INVISIBLE);
        textViewStart = (TextView) findViewById(R.id.textViewPlay);
        textViewStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TrackerService.newIntent(MainActivity.this);
                if (mButtonSetStep.isEnabled()) {
                    mButtonSetStep.setEnabled(false);
                    countDownTimer.start();
                    textViewStart.setText("stop");
                    intent.putExtra("start", true);
                    startService(intent);
                    return;
                }

                stopService(intent);
                Log.i(TAG, "service should be stopped");
                countDownTimer.cancel();
                textViewStart.setText("start");
                mButtonSetStep.setEnabled(true);


            }
        });

        ranger = (Ranger) findViewById(R.id.listener_ranger);
        ranger.setSelectedDay(5, false, 10l);
        ranger.setDayViewOnClickListener(new Ranger.DayViewOnClickListener() {
            @Override
            public void onDaySelected(int day) {
                countDownTimer.cancel();

                countDownTimer = new CustomCountDown(day * 60 * 1000, 1000);
                hiddenView.setVisibility(View.INVISIBLE);

                View parentLayout = findViewById(android.R.id.content);
                if (day == 1) {
                    Snackbar.make(parentLayout, "Step set for: " + day + " min", Snackbar.LENGTH_SHORT).show();
                } else
                    Snackbar.make(parentLayout, "Step set for: " + day + " mins", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpCounter() {
        mCounter = (TextView) findViewById(R.id.textView);
        countDownTimer = new CustomCountDown(300000, 1000);
    }

    private void setUpWheelIndicator() {
        wheelIndicatorView = (WheelIndicatorView) findViewById(R.id.wheel_indicator_view);
        wheelIndicatorView.setFilledPercent(0);
        WheelIndicatorItem timeProgressIndicator = new WheelIndicatorItem(0.1f, Color.parseColor("#62B9D5"));
        wheelIndicatorView.addWheelIndicatorItem(timeProgressIndicator);
    }

    public class CustomCountDown extends CountDownTimer {
        private double mStartTime;

        public CustomCountDown(long startTime, long interval) {
            super(startTime, interval);
            mStartTime = startTime / 1000;
        }


        @Override
        public void onTick(long l) {
            int a = (int) l / 1000;
            if (a > 999) {
                mCounter.setTextSize(60);
            }
            mCounter.setText("" + a);
            int percent = (int) ((mStartTime - a) / mStartTime * 100);
            wheelIndicatorView.setFilledPercent(percent + 1);
            wheelIndicatorView.notifyDataSetChanged();
        }

        @Override
        public void onFinish() {
            mCounter.setText("0");
            this.start();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mButtonSetStep.isEnabled()) {
            super.onBackPressed();

        }
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "Tracking...", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = TrackerService.newIntent(MainActivity.this);
        stopService(intent);
    }
}
