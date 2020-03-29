package com.pakholchuk.animalsalarmclock.adapter;

import com.pakholchuk.animalsalarmclock.AlarmClock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.pakholchuk.animalsalarmclock.AlarmReceiver;
import com.pakholchuk.animalsalarmclock.R;
import com.pakholchuk.animalsalarmclock.helper.AlarmDBHelper;
import com.pakholchuk.animalsalarmclock.helper.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class AlarmRecyclerAdapter extends RecyclerView.Adapter<AlarmRecyclerAdapter.AlarmsViewHolder> implements ItemTouchHelperAdapter {

    private AlarmDBHelper dbHelper;
    private AlarmManager alarmManager;
    private PendingIntent pIntent;
    private Context context;


    public static final String INTENT_ACTION = "ACTION_ALARM";

    public void cancelAll() {
        dbHelper.deleteAll();
        alarms.clear();
        notifyDataSetChanged();
    }

    class AlarmsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAlarmTime;

        AlarmsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlarmTime = itemView.findViewById(R.id.tv_alarm_time);
        }

        public void bind(AlarmClock alarmClock) {
            String am = "AM";
            if (alarmClock.isPm()){
                am = "PM";
            }
            String time = String.format(Locale.getDefault(), "%02d", alarmClock.getHour())
                    + ":"
                    + String.format(Locale.getDefault(), "%02d", alarmClock.getMinute())
                    + am;
            tvAlarmTime.setText(time);
        }
    }

    private ArrayList<AlarmClock> alarms = new ArrayList<>();

    public void initAlarmsList() {
        alarms = (ArrayList<AlarmClock>) dbHelper.getAllAlarms();
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addNewAlarm(AlarmClock newAlarm){
        AlarmClock alarm = dbHelper.addAlarm(newAlarm);
        alarms.add(alarm);
        setAlarm(alarm);
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarm(AlarmClock alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmClock.ID, alarm.getInsertRowId());
        int requestCode = (int) alarm.getInsertRowId();
        pIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarm.getTime(), pIntent);
    }

    public void cancelAlarm(long id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmClock.ID, id);
        PendingIntent cancelIntent =
                PendingIntent.getBroadcast(context, (int) id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(cancelIntent);
    }

    public List<AlarmClock> getAllAlarms(){
        return dbHelper.getAllAlarms();
    }

    public AlarmRecyclerAdapter(Context context) {
        dbHelper = new AlarmDBHelper(context);
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @NonNull
    @Override
    public AlarmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alarm, parent, false);
        return new AlarmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmsViewHolder holder, int position) {
        holder.bind(alarms.get(position));
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    // TODO: sort alarms by order
    @Override
    public boolean onItemMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(alarms, i, i+1);
            }

        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(alarms, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismissed(int position) {
        cancelAlarm(alarms.get(position).getInsertRowId());
        dbHelper.deleteAlarm(alarms.get(position).getInsertRowId());
        alarms.remove(position);
        notifyItemRemoved(position);
    }

    public void close() {

    }
}
