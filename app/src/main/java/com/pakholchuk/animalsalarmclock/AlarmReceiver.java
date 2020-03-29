package com.pakholchuk.animalsalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.pakholchuk.animalsalarmclock.adapter.AlarmRecyclerAdapter;


public class AlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            onReboot(context);
            return;
        }

        long id = intent.getLongExtra(AlarmClock.ID, -1);
        Intent startWakeUp = new Intent(context, WakeUpActivity.class);
        startWakeUp.putExtra(AlarmClock.ID, id);
        startWakeUp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startWakeUp);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onReboot(Context context) {
        AlarmRecyclerAdapter alarmAdapter = new AlarmRecyclerAdapter(context);
        for (AlarmClock alarmClock : alarmAdapter.getAllAlarms()) {
            alarmAdapter.setAlarm(alarmClock);
        }
    }

}
