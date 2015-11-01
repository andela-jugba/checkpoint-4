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
import android.widget.NumberPicker;
import android.widget.TextView;

import com.dlazaro66.wheelindicatorview.WheelIndicatorItem;
import com.dlazaro66.wheelindicatorview.WheelIndicatorView;

public class MainActivity extends AppCompatActivity {
    WheelIndicatorView wheelIndicatorView;
    CountDownTimer countDownTimer;

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
        int percentageOfExerciseDone = (int) (totalKmsDone/dailyKmsTarget * 100); //
        wheelIndicatorView.setFilledPercent(percentageOfExerciseDone);

        WheelIndicatorItem bikeActivityIndicatorItem = new WheelIndicatorItem(0.1f, Color.parseColor("#62B9D5"));


        wheelIndicatorView.addWheelIndicatorItem(bikeActivityIndicatorItem);

        NumberPicker num = (NumberPicker) findViewById(R.id.numberPicker);
        num.setMaxValue(10);
        num.setMinValue(1);
        num.setWrapSelectorWheel(true);
        num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                countDownTimer.cancel();

                countDownTimer = new CustomCountDown(i1 * 60 *1000,1000);
                countDownTimer.start();
//                wheelIndicatorView.setFilledPercent(i1);
//                wheelIndicatorView.notifyDataSetChanged();
            }
        });

        mCounter = (TextView) findViewById(R.id.textView);
        countDownTimer = new CustomCountDown(60000,1000);
        mCounter.setText(String.valueOf(10));
        countDownTimer.start();
    }

    public class CustomCountDown extends CountDownTimer{
        private double mStartTime;

        public CustomCountDown(long startTime, long interval){
            super(startTime,interval);
            mStartTime = startTime/1000;
        }


        @Override
        public void onTick(long l) {
            double a = l/1000;

            mCounter.setText("" + l / 1000);
            int percent = (int) ((mStartTime - a)/mStartTime * 100);
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
