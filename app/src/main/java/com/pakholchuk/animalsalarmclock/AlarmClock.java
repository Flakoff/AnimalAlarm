package com.pakholchuk.animalsalarmclock;

import android.widget.Button;
import android.widget.ImageButton;

import java.time.DayOfWeek;

class AlarmClock {

    private int hour;
    private int minute;
    private boolean[] days = new boolean[7];

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
