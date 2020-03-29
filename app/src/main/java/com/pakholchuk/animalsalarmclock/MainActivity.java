package com.pakholchuk.animalsalarmclock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pakholchuk.animalsalarmclock.adapter.AlarmRecyclerAdapter;
import com.pakholchuk.animalsalarmclock.helper.RecyclerItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    /* <a href="https://www.freepik.com/free-photos-vectors/cartoon">Cartoon vector created by freepik - www.freepik.com</a>
    *Photo by eberhard grossgasteiger on Unsplash
    * Photo by Paweł Czerwiński on Unsplash
    * TODO: настроить сохранение в памяти
    */
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.switch_am_pm) Switch switchAmPm;
    @BindView(R.id.tv_new_alarm_time) TextView tvNewAlarmTime;

    @BindView(R.id.iv_hour) ImageView ivHour;
    @BindView(R.id.iv_minute) ImageView ivMinute;

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
    @BindView(R.id.ib_time_0) ImageButton ibTime0;

    boolean isPm;
    int hour;
    int minute;
    final String LOG_TAG = "myTag";

    RecyclerView recyclerView;
    AlarmRecyclerAdapter alarmAdapter;
    private AboutFragment fragmentAbout;
    private boolean isFragmentOnTop = false;

    @Override
    public void onBackPressed() {
        if(isFragmentOnTop) {
            getSupportFragmentManager().beginTransaction().remove(fragmentAbout).commit();
            isFragmentOnTop = false;
        }
         else super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showCurrentTime();
        initRecyclerView();
        fragmentAbout = new AboutFragment();

        floatingActionButton.setOnClickListener(fabMainListener);
        Log.d(LOG_TAG, "fabMainListener added");
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAlarmAdapter();
        recyclerView.setAdapter(alarmAdapter);
        alarmAdapter.initAlarmsList();
        initRecyclerTouchHelper();
    }

    private void initAlarmAdapter() {
        if (alarmAdapter == null) {
            alarmAdapter = new AlarmRecyclerAdapter(this);
        }
    }

    private void initRecyclerTouchHelper() {
        ItemTouchHelper.Callback callback = new RecyclerItemTouchHelperCallback(alarmAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    View.OnClickListener fabMainListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setNewAlarm();
            floatingActionButton.hide();
        }
    };

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_about:
                showFragmentAbout();
                return true;
            case R.id.item_cancel_all:
                alarmAdapter.cancelAll();
                return true;
            default:
                return false;
        }
    }

    class HourOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String resName = getResources().getResourceEntryName(v.getId()).substring(9);
            setHourAndPm(Integer.parseInt(resName));
            setViewVisibilitiesOnSetMinutes();
            changeNewAlarmTextView();
            setButtonsOnClick(new MinuteOnClickListener());
        }
    }

    private void changeNewAlarmTextView() {
        String am = "AM";
        if (isPm){
            am = "PM";
        }
        String time = String.format(Locale.getDefault(), "%02d", hour)
                + ":"
                + String.format(Locale.getDefault(), "%02d", minute)
                + am;
        tvNewAlarmTime.setText(time);
    }

    private void setHourAndPm(int hour) {
        isPm = switchAmPm.isChecked();
        this.hour=hour;
        ivHour.setRotation(hour*30);
    }

    private void setViewVisibilitiesOnSetMinutes() {
        switchAmPm.setVisibility(View.INVISIBLE);
        ivMinute.setRotation(0);
        ivMinute.setVisibility(View.VISIBLE);
    }

    class MinuteOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String resName = getResources().getResourceEntryName(v.getId()).substring(9);
            setMinutes(5*Integer.parseInt(resName));
            changeFAB();
        }
    }

    private void changeFAB() {
        floatingActionButton.show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                addNewAlarmToAdapter();
                floatingActionButton.setOnClickListener(fabMainListener);
                tvNewAlarmTime.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "New Alarm added!", Toast.LENGTH_SHORT).show();
                showCurrentTime();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addNewAlarmToAdapter() {
        AlarmClock alarmClock = new AlarmClock();
        alarmClock.setHour(hour);
        alarmClock.setMinute(minute);
        alarmClock.setPm(isPm);
        alarmClock.setTime(getTimeInMillis());
        alarmAdapter.addNewAlarm(alarmClock);
    }

    private void setNewAlarm() {
        Log.d(LOG_TAG, "newAlarmStarted");
        setViewVisibilitiesOnNewAlarmPressed();
        setButtonsOnClick(new HourOnClickListener());
    }

    private void setViewVisibilitiesOnNewAlarmPressed() {
        tvNewAlarmTime.setVisibility(View.VISIBLE);
        switchAmPm.setVisibility(View.VISIBLE);
        ivMinute.setVisibility(View.INVISIBLE);
    }

    private void setMinutes(int minutes) {
        this.minute = minutes;
        ivMinute.setRotation(this.minute *6);
        ivHour.setRotation((hour*30)+(this.minute *0.5f));
        changeNewAlarmTextView();
    }

    private long getTimeInMillis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        if (!isPm) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, hour + 12);
        }
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return calendar.getTimeInMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAlarmAdapter();
        alarmAdapter.initAlarmsList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        alarmAdapter.close();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();
    }

    private void showFragmentAbout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.about_container, fragmentAbout).commit();
        isFragmentOnTop = true;
    }

    private void showCurrentTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        ivHour.setRotation((hour*30)+(minute/2));
        ivMinute.setRotation(minute*6);
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
        viewList.add(ibTime0);
        for (View v: viewList) {
            v.setOnClickListener(ibListener);
        }
    }

}
