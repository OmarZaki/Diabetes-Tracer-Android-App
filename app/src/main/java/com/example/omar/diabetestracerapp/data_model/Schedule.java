package com.example.omar.diabetestracerapp.data_model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.omar.diabetestracerapp.auxiliary.TypeEvent;

import java.util.Date;

/**
 * Created by OMAR on 12/26/2016.
 */

public class Schedule {
    public static final String _COL_TITLE="title";
    public static final String _COL_DATE="date";
    public static final String _COL_TYPE="event_type";
    public static final String _OBJECT="object";
    public static final String _SCHEDULE_TABLE ="schedule";
    public static String[] _SCHEDULE_COLL = {
            Schedule._COL_TITLE,
            Schedule._COL_DATE,
            Schedule._COL_TYPE};
    String title;
    Date date;
    TypeEvent type;


    public TypeEvent getType() {
        return type;
    }

    public void setType(TypeEvent type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public ContentValues getContentValuesObject() {
        ContentValues userFieldValues = new ContentValues();
        userFieldValues.put(Schedule._COL_TITLE, this.getTitle());
        userFieldValues.put(Schedule._COL_TYPE, String.valueOf(this.getType()));
        userFieldValues.put(Schedule._COL_DATE, this.getDate().toString());
        return userFieldValues;

    }


    public static Schedule getScheduleDoseObject(Cursor cursor) {
        Schedule schedule= new Schedule();
        schedule.setTitle(cursor.getString(cursor.getColumnIndex(Schedule._COL_TITLE)));
        schedule.setType(TypeEvent.valueOf(cursor.getString(cursor.getColumnIndex(Schedule._COL_TYPE))));
        schedule.setDate(User.ConvertStringToDateObjectFormDB(cursor.getString(cursor.getColumnIndex(Schedule._COL_DATE))));
        return schedule;
    }
}
