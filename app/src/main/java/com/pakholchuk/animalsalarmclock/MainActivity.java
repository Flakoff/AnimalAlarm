package com.pakholchuk.animalsalarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.switch_am_pm) Switch btnAm;
    @BindView(R.id.tv_new_time) TextView tvNewTime;

    @BindView(R.id.iv_hour) ImageView ivHour;
    @BindView(R.id.iv_minute) ImageView ivMinute;

    @BindView(R.id.btn_mon) Button btnMon;
    @BindView(R.id.btn_tue) Button btnTue;
    @BindView(R.id.btn_wed) Button btnWed;
    @BindView(R.id.btn_thu) Button btnThu;
    @BindView(R.id.btn_fri) Button btnFri;
    @BindView(R.id.btn_sat) Button btnSat;
    @BindView(R.id.btn_sun) Button btnSun;

    @BindView(R.id.ib_time_1) ImageButton ibTime1;
    @BindView(R.id.ib_time_2) ImageButton ibTime2;
    @BindView(R.id.ib_time_3) ImageButton ibTime3;
    @BindView(R.id.ib_time_4) ImageButton ibTime4;
    @BindView(R.id.ib_time_5) ImageButton ibTime5;
    @BindView(R.id.ib_time_6) ImageButton ibTime6;
    @BindView(R.id.ib_time_7) ImageButton ibTime7;
    @BindView(R.id.ib_time_8) ImageButton ibTime8;
    @BindView(R.id.ib_time_9) ImageButton ibTime9;
    @BindView(R.id.ib_time_10) ImageButton ibTime10;
    @BindView(R.id.ib_time_11) ImageButton ibTime11;
    @BindView(R.id.ib_time_12) ImageButton ibTime12;


    ArrayList<AlarmClock> listAlarms;
    ArrayList<View> daysList = new ArrayList<>();
    int hour;
    int minute;
    final String LOG_TAG = "LOG_TAG";

    View.OnClickListener fabMainListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            newAlarm();
            floatingActionButton.hide();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        showCurrentTime();
        floatingActionButton.setOnClickListener(fabMainListener);
        Log.d(LOG_TAG, "fabMainListener added");

    }

    class HourOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_time_1:{
                    setMinutes(1);
                    hour = 1;
                    break;
                }
                case R.id.ib_time_2:{
                    hour = 2;
                    break;
                }
                case R.id.ib_time_3:{
                    hour = 3;
                    break;
                }
                case R.id.ib_time_4:{
                    hour = 4;
                    break;
                }
                case R.id.ib_time_5:{
                    hour = 5;
                    break;
                }
                case R.id.ib_time_6:{
                    hour = 6;
                    break;
                }
                case R.id.ib_time_7:{
                    hour = 7;
                    break;
                }
                case R.id.ib_time_8:{
                    hour = 8;
                    break;
                }
                case R.id.ib_time_9:{
                    hour = 9;
                    break;
                }
                case R.id.ib_time_10:{
                    hour = 10;
                    break;
                }
                case R.id.ib_time_11:{
                    hour = 11;
                    break;
                }
                case R.id.ib_time_12:{
                    hour = 12;
                    break;
                }
                default:break;
            }

        }
    }

    class DayOnTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            v.performClick();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!v.isPressed()) {
                    Log.d(LOG_TAG, "setPressed " + v.getId());
                    v.setPressed(true);
                }
                else {
                    v.setPressed(false);
                    Log.d(LOG_TAG, "setUnpressed " + v.getId());
                }
            }
            return true;
        }
    }

    private void setMinutes(int hours) {

        String time = hours + ":00";
        hour = hours;
        Log.d(LOG_TAG, time + " , setMinutes");
        tvNewTime.setText(time);
        setButtonsOnClick(new MinuteOnClickListener());
    }

    class MinuteOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_time_1:{
                    setDays(5);
                    break;
                }
                case R.id.ib_time_2:{
                    minute = 10;
                    break;
                }
                case R.id.ib_time_3:{
                    minute = 15;
                    break;
                }
                case R.id.ib_time_4:{
                    minute = 20;
                    break;
                }
                case R.id.ib_time_5:{
                    minute = 25;
                    break;
                }
                case R.id.ib_time_6:{
                    minute = 30;
                    break;
                }
                case R.id.ib_time_7:{
                    minute = 35;
                    break;
                }
                case R.id.ib_time_8:{
                    minute = 40;
                    break;
                }
                case R.id.ib_time_9:{
                    minute = 45;
                    break;
                }
                case R.id.ib_time_10:{
                    minute = 50;
                    break;
                }
                case R.id.ib_time_11:{
                    minute = 55;
                    break;
                }
                case R.id.ib_time_12:{
                    minute = 0;
                    break;
                }
                default:break;
            }

        }
    }


    private void setDays(int minutes) {
        Log.d(LOG_TAG, "setDays()");
        String time = hour + ":" + minutes;
        minute = minutes;
        tvNewTime.setText(time);
        setDaysOnClick(new DayOnTouchListener());
        floatingActionButton.show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmClock alarmClock = new AlarmClock();
                alarmClock.setHour(hour);
                alarmClock.setMinute(minute);
                boolean[] days = new boolean[7];
                for (int i = 0; i < 7; i++){
                    days[i] = daysList.get(i).isPressed();
                }
                alarmClock.setDays(days);
                floatingActionButton.setOnClickListener(fabMainListener);
            }
        });
    }

    private void showCurrentTime() {

    }

    private void setDaysOnClick(View.OnTouchListener listener){
        Log.d(LOG_TAG, "setDaysOnClick");
        daysList.clear();
        daysList.add(btnMon);
        daysList.add(btnTue);
        daysList.add(btnWed);
        daysList.add(btnThu);
        daysList.add(btnFri);
        daysList.add(btnSat);
        daysList.add(btnSun);
        for (View v : daysList){
            v.setOnTouchListener(listener);
        }
    }
    private void setButtonsOnClick(View.OnClickListener ibListener){
        Log.d(LOG_TAG, "setButtonsOnClick" + ibListener.getClass().toString());
        ArrayList<View> viewList = new ArrayList<>();
        viewList.add(ibTime1);
        viewList.add(ibTime2);
        viewList.add(ibTime3);
        viewList.add(ibTime4);
        viewList.add(ibTime5);
        viewList.add(ibTime6);
        viewList.add(ibTime7);
        viewList.add(ibTime8);
        viewList.add(ibTime9);
        viewList.add(ibTime10);
        viewList.add(ibTime11);
        viewList.add(ibTime12);
        for (View v: viewList) {
            v.setOnClickListener(ibListener);

        }

    }

    private void newAlarm() {
        Log.d(LOG_TAG, "newAlarmStarted");
        String defaultTime = "00:00";
        tvNewTime.setVisibility(View.VISIBLE);
        tvNewTime.setText(defaultTime);
        ivMinute.setVisibility(View.INVISIBLE);
        setButtonsOnClick(new HourOnClickListener());
    }

}
