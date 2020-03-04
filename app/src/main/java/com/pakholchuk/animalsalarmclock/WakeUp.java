package com.pakholchuk.animalsalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pakholchuk.animalsalarmclock.helper.AlarmDBHelper;

import java.util.Locale;

public class WakeUp extends AppCompatActivity {

    AlarmDBHelper dbHelper;
    long alarmId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_wake_up);

        dbHelper = new AlarmDBHelper(this);

        Intent intent = getIntent();
        alarmId = intent.getLongExtra(AlarmClock.ID, -1);
        AlarmClock alarm = dbHelper.getAlarmById(alarmId);

        TextView timeText = findViewById(R.id.wake_up_time_text_view);
        String am = "AM";
        if (alarm.isPm()){
            am = "PM";
        }
        String time = String.format(Locale.getDefault(), "%02d", alarm.getHour())
                + ":"
                + String.format(Locale.getDefault(), "%02d", alarm.getMinute())
                + am;
        timeText.setText(time);

        Button cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteAlarm(alarmId);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dbHelper.deleteAlarm(alarmId);
        finish();
    }
}
