package com.pakholchuk.animalsalarmclock.adapter;

import com.pakholchuk.animalsalarmclock.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.pakholchuk.animalsalarmclock.R;
import com.pakholchuk.animalsalarmclock.helper.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class AlarmRecyclerAdapter extends RecyclerView.Adapter<AlarmRecyclerAdapter.AlarmsViewHolder> implements ItemTouchHelperAdapter {

    class AlarmsViewHolder extends RecyclerView.ViewHolder {
        private Switch switchAlarm;
        private TextView tvAlarmTime;
        private TextView tvAMon;
        private TextView tvATue;
        private TextView tvAWed;
        private TextView tvAThu;
        private TextView tvAFri;
        private TextView tvASat;
        private TextView tvASun;

        public AlarmsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlarmTime = itemView.findViewById(R.id.tv_alarm_time);
            tvAMon = itemView.findViewById(R.id.tv_a_mon);
            tvATue = itemView.findViewById(R.id.tv_a_tue);
            tvAWed = itemView.findViewById(R.id.tv_a_wed);
            tvAThu = itemView.findViewById(R.id.tv_a_thu);
            tvAFri = itemView.findViewById(R.id.tv_a_fri);
            tvASat = itemView.findViewById(R.id.tv_a_sat);
            tvASun = itemView.findViewById(R.id.tv_a_sun);
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

            ArrayList<TextView> tViews = new ArrayList<>();
            tViews.add(tvAMon);
            tViews.add(tvATue);
            tViews.add(tvAWed);
            tViews.add(tvAThu);
            tViews.add(tvAFri);
            tViews.add(tvASat);
            tViews.add(tvASun);
            int color = tvAlarmTime.getResources().getColor(R.color.colorAccent);

            for (int i = 0; i < 7; i++){
                if ((alarmClock.getDays())[i]) {
                    (tViews.get(i)).setBackgroundColor(color);
                }
            }
        }
    }

    private ArrayList<AlarmClock> alarms = new ArrayList<>();

    public void addNewAlarm(AlarmClock newAlarm){
        alarms.add(newAlarm);
        notifyDataSetChanged();
    }

    public AlarmRecyclerAdapter() {
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
        alarms.remove(position);
        notifyItemRemoved(position);

    }

}
