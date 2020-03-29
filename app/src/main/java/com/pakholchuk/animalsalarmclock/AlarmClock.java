package com.pakholchuk.animalsalarmclock;

public class AlarmClock {

    public static final String ID = "alarmId";

    private long time;
    private int hour;
    private int minute;
    private boolean isPm;
    private long insertRowId;

    public AlarmClock(){

    }

    public AlarmClock(long time, int hour, int minute, boolean isPm) {
        this.time = time;
        this.hour = hour;
        this.minute = minute;
        this.isPm = isPm;
    }

    public AlarmClock(long time, int hour, int minute, boolean isPm, long insertRowId) {
        this.time = time;
        this.hour = hour;
        this.minute = minute;
        this.isPm = isPm;
        this.insertRowId = insertRowId;
    }

    public boolean isPm() {
        return isPm;
    }

    public void setPm(boolean pm) {
        isPm = pm;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getInsertRowId() {
        return insertRowId;
    }

    public void setInsertRowId(long insertRowId) {
        this.insertRowId = insertRowId;
    }
}
