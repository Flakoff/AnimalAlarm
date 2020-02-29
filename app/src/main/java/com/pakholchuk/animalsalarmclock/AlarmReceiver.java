package com.pakholchuk.animalsalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;


public class AlarmReceiver extends BroadcastReceiver {
    final String WAKE_TAG = "alarm:wakelocktag";

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_TAG);
        wakeLock.acquire();
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        wakeLock.release();
    }

    public void toggleAlarm(Context context, Boolean fire){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);


    }
}
