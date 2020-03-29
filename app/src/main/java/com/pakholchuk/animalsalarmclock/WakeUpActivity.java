package com.pakholchuk.animalsalarmclock;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pakholchuk.animalsalarmclock.helper.AlarmDBHelper;

import java.util.Locale;

public class WakeUpActivity extends AppCompatActivity {

    private AlarmDBHelper dbHelper;
    private long alarmId;
    private MediaPlayer mp;
    private AlarmClock alarm;
    private TextView timeText;
    private Button cancelButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_wake_up);

        timeText = findViewById(R.id.wake_up_time_text_view);
        cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
            }
        });

        getAlarmFromDb();
        setTimeTextView();
        startAlarm();
    }

    private void setTimeTextView() {
        String time = String.format(Locale.getDefault(), "%02d", alarm.getHour())
                + ":"
                + String.format(Locale.getDefault(), "%02d", alarm.getMinute());
        timeText.setText(time);
    }

    private void getAlarmFromDb() {
        dbHelper = new AlarmDBHelper(this);
        alarmId = getIntent().getLongExtra(AlarmClock.ID, -1);
        alarm = dbHelper.getAlarmById(alarmId);
    }

    private void startAlarm() {
        Uri notification = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_ALARM);
        mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopAlarm();
    }

    private void stopAlarm() {
        dbHelper.deleteAlarm(alarmId);
        mp.stop();
        finish();
    }
}
