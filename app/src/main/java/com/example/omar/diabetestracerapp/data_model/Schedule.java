package com.example.omar.diabetestracerapp.data_model;

import com.example.omar.diabetestracerapp.auxiliary.TypeEvent;

import java.util.Date;

/**
 * Created by OMAR on 12/26/2016.
 */

public class Schedule {
    public static final String _COL_TITLE="title";
    public static final String _COL_DATE="date";
    public static final String _COL_TYPE="Type";
    public static final String _OBJECT="object";
    public static final String _SCHEDULE_TABLE ="schedule";

    String title;
    Date date;
    TypeEvent type;
    Object object;


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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }





}
