package com.andela.checkpoint.onestep.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.andela.checkpoint.onestep.R;
import com.andela.checkpoint.onestep.database.TimePreference;
import com.andela.checkpoint.onestep.services.TrackerService;
import com.andressantibanez.ranger.Ranger;
import com.dlazaro66.wheelindicatorview.WheelIndicatorItem;
import com.dlazaro66.wheelindicatorview.WheelIndicatorView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private WheelIndicatorView mWheelIndicatorView;
    private CountDownTimer mCountDownTimer;
    private Button mButtonSetStep;
    private TextView mTextViewStart;
    private TextView mTextViewSec;
    private Ranger mRanger;

    private View mHiddenView;
    private TextView mCounter;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpBasicUI();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    boolean update = bundle.getBoolean(TrackerService.RESULT, false);
                    if (update) {
                        mCountDownTimer.cancel();
                        mCountDownTimer.start();

                    }
                }
            }
        };

    }

    public void onClickButton(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.buttonLog:
                Intent intent = new Intent(MainActivity.this, LocationListActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonSetStep:
                int visibility = mHiddenView.getVisibility();

                if (visibility == View.VISIBLE) {
                    mHiddenView.setVisibility(View.INVISIBLE);
                } else mHiddenView.setVisibility(View.VISIBLE);
                break;
            case R.id.textViewPlay:
                Intent intent1 = TrackerService.newIntent(MainActivity.this);
                if (mButtonSetStep.isEnabled()) {
                    mButtonSetStep.setEnabled(false);
                    mCountDownTimer.start();
                    mTextViewStart.setText(R.string.app_stop);
                    intent1.putExtra("start", true);
                    startService(intent1);
                    return;
                }

                stopService(intent1);
                Log.i(TAG, "service should be stopped");
                mCountDownTimer.cancel();
                mTextViewStart.setText(R.string.app_start);
                mButtonSetStep.setEnabled(true);
                break;
        }
    }


    private void setUpBasicUI() {
        setUpWheelIndicator();
        setUpCounter();

        mHiddenView = findViewById(R.id.hiddenView);
        mButtonSetStep = (Button) findViewById(R.id.buttonSetStep);
        mTextViewStart = (TextView) findViewById(R.id.textViewPlay);
        mTextViewSec = (TextView) findViewById(R.id.textViewSec);
        mRanger = (Ranger) findViewById(R.id.listener_ranger);
        int time = TimePreference.getStoredTime(getApplicationContext());

        mRanger.setSelectedDay(time, false, Ranger.DELAY_SELECTION);
        mRanger.setDayViewOnClickListener(new Ranger.DayViewOnClickListener() {
            @Override
            public void onDaySelected(int day) {
                mCountDownTimer.cancel();

                TimePreference.setStoredTime(getApplicationContext(), day);
                mCountDownTimer = new CustomCountDown(day * 60 * 1000, 1000);
                mHiddenView.setVisibility(View.INVISIBLE);

                View parentLayout = findViewById(android.R.id.content);
                if (day == 1) {
                    Snackbar.make(parentLayout, getString(R.string.app_step_set) + day + getString(R.string.app_minute), Snackbar.LENGTH_SHORT).show();
                } else
                    Snackbar.make(parentLayout, getString(R.string.app_step_set) + day + getString(R.string.app_minutes), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpCounter() {
        mCounter = (TextView) findViewById(R.id.textView);
        int time = TimePreference.getStoredTime(getApplicationContext());
        mCountDownTimer = new CustomCountDown(time * 60 * 1000, 1000);
    }

    private void setUpWheelIndicator() {
        mWheelIndicatorView = (WheelIndicatorView) findViewById(R.id.wheel_indicator_view);
        mWheelIndicatorView.setFilledPercent(0);
        WheelIndicatorItem timeProgressIndicator = new WheelIndicatorItem(0.1f, Color.parseColor("#62B9D5"));
        mWheelIndicatorView.addWheelIndicatorItem(timeProgressIndicator);
    }

    private class CustomCountDown extends CountDownTimer {
        private double mStartTime;

        public CustomCountDown(long startTime, long interval) {
            super(startTime, interval);
            mStartTime = startTime / 1000;
        }


        @Override
        public void onTick(long l) {
            int currentTime = (int) l / 1000;
            if (currentTime > 60) {
                mCounter.setTextSize(45);
                mTextViewSec.setText("mins");
            } else {
                mCounter.setTextSize(71);
                mTextViewSec.setText("secs");
            }
            mCounter.setText(formatText(currentTime));
            int percent = (int) ((mStartTime - currentTime) / mStartTime * 100);
            mWheelIndicatorView.setFilledPercent(percent + 1);
            mWheelIndicatorView.notifyDataSetChanged();
        }

        @Override
        public void onFinish() {
            mCounter.setText("0");
            this.start();
        }
    }

    private String formatText(int time) {
        int mins = time / 60;
        int secs = time - (mins * 60);
        if (mins == 0) {
            return "" + secs;
        }
        return "" + mins + ":" + secs;

    }

    @Override
    public void onBackPressed() {
        if (mButtonSetStep.isEnabled()) {
            super.onBackPressed();

        }
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, R.string.app_tracking, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = TrackerService.newIntent(MainActivity.this);
        stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, new IntentFilter(TrackerService.NOTIFICATION));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }
}
