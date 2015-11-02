package com.andela.checkpoint.onestep;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.andressantibanez.ranger.Ranger;
import com.dlazaro66.wheelindicatorview.WheelIndicatorItem;
import com.dlazaro66.wheelindicatorview.WheelIndicatorView;

import static android.view.View.TEXT_DIRECTION_FIRST_STRONG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    WheelIndicatorView wheelIndicatorView;
    CountDownTimer countDownTimer;
    private Button mButtonSetStep;
    private Button buttonPlay;
    private TextView textViewStart;

    private View hiddenView;
    private TextView mCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        wheelIndicatorView = (WheelIndicatorView) findViewById(R.id.wheel_indicator_view);

        //WheelIndicatorView wheelIndicatorView = new WheelIndicatorView(this);
        // dummy data
        float dailyKmsTarget = 10.0f; // 4.0Km is the user target, for example
        float totalKmsDone = 10.0f; // User has done 3 Km
        int percentageOfExerciseDone = (int) (totalKmsDone / dailyKmsTarget * 100); //
        wheelIndicatorView.setFilledPercent(percentageOfExerciseDone);

        WheelIndicatorItem bikeActivityIndicatorItem = new WheelIndicatorItem(0.1f, Color.parseColor("#62B9D5"));


        wheelIndicatorView.addWheelIndicatorItem(bikeActivityIndicatorItem);


        mCounter = (TextView) findViewById(R.id.textView);
        countDownTimer = new CustomCountDown(60000, 1000);


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
                if (mButtonSetStep.isEnabled()) {
                    mButtonSetStep.setEnabled(false);
                    countDownTimer.start();
                    textViewStart.setText("stop");
                    return;
                }
                countDownTimer.cancel();
                textViewStart.setText("start");
                mButtonSetStep.setEnabled(true);
            }
        });
        Ranger ranger = (Ranger) findViewById(R.id.listener_ranger);
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

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id != R.id.hiddenView) {
            Toast.makeText(MainActivity.this, "this works", Toast.LENGTH_SHORT).show();
            hiddenView.setVisibility(View.INVISIBLE);
        }

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
//            wheelIndicatorView.setFilledPercent(100);
//            wheelIndicatorView.notifyDataSetChanged();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
