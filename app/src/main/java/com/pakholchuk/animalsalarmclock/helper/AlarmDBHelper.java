package com.pakholchuk.animalsalarmclock.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.pakholchuk.animalsalarmclock.AlarmClock;

import java.util.ArrayList;
import java.util.List;

public class AlarmDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "alarms.db";
    public static final int DATABASE_VERSION = 5;
    public static final String TABLE_NAME = "alarms";

    public static final String FIELD_TIME = "time";
    public static final String FIELD_HOUR = "hour";
    public static final String FIELD_MINUTE = "minute";

    private static final String TEXT_TYPE = " TEXT";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + AlarmClock.ID + " INTEGER PRIMARY KEY,"
            + FIELD_TIME + TEXT_TYPE + ","
            + FIELD_HOUR + TEXT_TYPE + ","
            + FIELD_MINUTE + TEXT_TYPE + ")";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private SQLiteDatabase database;


    private String[] projection = {AlarmClock.ID, FIELD_TIME, FIELD_HOUR, FIELD_MINUTE};

    public AlarmDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public AlarmClock addAlarm(AlarmClock alarmClock) {
        ContentValues cv = new ContentValues();
        cv.put(FIELD_TIME, String.valueOf(alarmClock.getTime()));
        cv.put(FIELD_HOUR, String.valueOf(alarmClock.getHour()));
        cv.put(FIELD_MINUTE, String.valueOf(alarmClock.getMinute()));
        long insertRowId = database.insert(TABLE_NAME, null, cv);
        Log.d("myTag", "dbrequest = " + insertRowId);
        return new AlarmClock(alarmClock.getTime(), alarmClock.getHour(), alarmClock.getMinute(),
                alarmClock.getDays(), alarmClock.isPm(), insertRowId);
    }

    public void deleteAlarm(long id) {
        database.delete(TABLE_NAME, AlarmClock.ID + "=" + id, null);
    }

    public void deleteAlarm(AlarmClock alarmClock) {
        deleteAlarm(alarmClock.getInsertRowId());
    }

    public List<AlarmClock> getAllAlarms() {
        List<AlarmClock> alarmClocks = new ArrayList<AlarmClock>();
        Cursor cursor = database.query(TABLE_NAME, projection, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AlarmClock alarmClock = cursorToAlarm(cursor);
            alarmClocks.add(alarmClock);
            cursor.moveToNext();
        }
        cursor.close();
        return alarmClocks;
    }

    private AlarmClock cursorToAlarm(Cursor cursor) {
        AlarmClock alarmClock = new AlarmClock();
        alarmClock.setInsertRowId(Long.parseLong(cursor.getString(0)));
        alarmClock.setTime(Long.parseLong(cursor.getString(1)));
        alarmClock.setHour(Integer.parseInt(cursor.getString(2)));
        alarmClock.setMinute(Integer.parseInt(cursor.getString(3)));
        return alarmClock;
    }

    public AlarmClock getAlarmByPosition(int position) {
        List<AlarmClock> alarms = getAllAlarms();
        if (position <= alarms.size()) return alarms.get(position);
        return null;
    }

    public AlarmClock getAlarmById(long alarmId) {
        for (AlarmClock alarmClock : getAllAlarms()) {
            long id = alarmClock.getInsertRowId();
            if (id == alarmId) return alarmClock;
        }
        return null;
    }


    public void deleteAll() {
        List<AlarmClock> alarmClocks = new ArrayList<AlarmClock>();
        Cursor cursor = database.query(TABLE_NAME, projection, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AlarmClock alarmClock = cursorToAlarm(cursor);
            alarmClocks.add(alarmClock);
            cursor.moveToNext();
        }
        cursor.close();
        for (AlarmClock alarm : alarmClocks)
        {
            deleteAlarm(alarm);
        }
    }
}
