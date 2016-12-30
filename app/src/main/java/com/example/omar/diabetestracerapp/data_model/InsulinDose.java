package com.example.omar.diabetestracerapp.data_model;


import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by OMAR on 12/9/2016.
 */
public class InsulinDose {
    public final static String _INSULIN_DOSE_TITLE="Insulin Dose";
    public final static String _INSULIN_DOSE_TABLE = "insulinDose";
    public static final String _DATE_FORMAT_NOW="yyyy-MM-dd HH:mm:ss";

    public static String[] _INSULIN_COLS = {InsulinDose._ID,
            InsulinDose._ORIGANL_ID,
            InsulinDose._TAKEN,
            InsulinDose._QUANTITY,
            InsulinDose._DATE_TIME};
    // InsulinDose Column names
    public final static String _ID = "id";
    public final static String _QUANTITY = "quantity";
    public final static String _TAKEN = "taken";
    public final static String _DATE_TIME = "date_time";
    public final static String _USERS_ID = "Users_id";
    public static final String _ORIGANL_ID = "o_id";

    /**
     * Fields
     */
    private int id;
    private int quantity;
    private Date date_time;



    private int original_id;
    private Boolean taken;
    private int Users_id;

    /**
     * properties
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public int getUsers_id() {
        return Users_id;
    }

    public void setUsers_id(int users_id) {
        Users_id = users_id;
    }

    public Boolean getTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public boolean validate() {
        if (this.getQuantity() > 0 &&
                this.getDate_time() != null)
            return true;
        return false;
    }

    /**
     * get contentValues for the current InsulinDose object.
     *
     * @return
     */
    public ContentValues getContentValuesObject(){
        ContentValues userFieldValues = new ContentValues();
        userFieldValues.put(InsulinDose._ID, this.getId());
        userFieldValues.put(InsulinDose._ORIGANL_ID, getOriginal_id());
        userFieldValues.put(InsulinDose._QUANTITY, this.getQuantity());
        userFieldValues.put(InsulinDose._TAKEN, this.getTaken());
        userFieldValues.put(InsulinDose._DATE_TIME, this.getDate_time().getTime());
        userFieldValues.put(InsulinDose._USERS_ID, this.getUsers_id());


        return userFieldValues;
    }

    /**
     * Get getOriginal id;
     * @return
     */
    public int getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(int original_id) {
        this.original_id = original_id;
    }

    /**
     * get InsulinDoseObject from Cursor object.
     * @param cursor
     * @return
     */
    public static InsulinDose getInsulinDoseObject(Cursor cursor) {
        InsulinDose insulinDose = new InsulinDose();
        insulinDose.setId(cursor.getInt(cursor.getColumnIndex(InsulinDose._ID)));
        insulinDose.setQuantity(cursor.getInt(cursor.getColumnIndex(InsulinDose._QUANTITY)));
        insulinDose.setTaken(cursor.getInt(cursor.getColumnIndex(InsulinDose._TAKEN))>0);
        insulinDose.setOriginal_id(cursor.getInt(cursor.getColumnIndex(InsulinDose._ORIGANL_ID)));
        insulinDose.setDate_time(new Date(cursor.getLong(cursor.getColumnIndex(InsulinDose._DATE_TIME))));
        return insulinDose;
    }


    public static String getDateFromDateTimeObject(Date dateObj){

        SimpleDateFormat sdf = new SimpleDateFormat(InsulinDose._DATE_FORMAT_NOW);
        String fullDateString = sdf.format(dateObj);
        String []date_time = fullDateString.split(" ");
        return date_time[0];
    }
    public static String getTimeFromDateTimeObejct(Date dateObj){

        SimpleDateFormat sdf = new SimpleDateFormat(InsulinDose._DATE_FORMAT_NOW);
        String fullDateString = sdf.format(dateObj);
        String []date_time = fullDateString.split(" ");
        return date_time[1];
    }

    public static String convertListToJson(List<InsulinDose> insulinDose) {
        Gson gson = new Gson();
        String insulinDoseAsString = gson.toJson(insulinDose);
        return insulinDoseAsString;
    }
    public static ArrayList<InsulinDose> convertJsonToList(String insulinDose) {
        Gson gson =new  GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.S")
                .create();
        ArrayList<InsulinDose> insulinDoseAsString = gson.fromJson(insulinDose,new TypeToken<ArrayList<InsulinDose>>(){}.getType());
        return insulinDoseAsString;
    }
    /**
     * Convert on String JSON object of InsulinDose to 	InsulinDose Java Object;
     * @param insulinDoseAsString
     * @return
     */
    public static InsulinDose convertStringToObject(String insulinDoseAsString){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.S")
                .create();
        InsulinDose dose = gson.fromJson(insulinDoseAsString, InsulinDose.class);
        return dose;
    }
    /**
     * convert Object to Json string.
     * @param insulinDose
     * @return
     */
    public static String convertObjectToString(InsulinDose insulinDose){
        Gson gson = new  GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
                .create();
        String dose= gson.toJson(insulinDose);
        return dose;
    }


    public static org.json.JSONObject toJsonObject(InsulinDose dose){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(convertObjectToString(dose));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * get the the time from Date Object.
     *
     * @param currentDate
     * @return return a date format.
     */
    public static String getCurrentTime(Date currentDate) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(currentDate);   // assigns calendar to given date
        long hours = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        long minutes = calendar.get(Calendar.MINUTE);        // get current format;
        return hours+":"+ minutes;
    }

    /**
     * get the hours from the date object.
     * @param currentDate
     * @return
     */
    public static Long getHours(Date currentDate){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(currentDate);
        //if(hours==0) hours=23;
        return (long) calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * get minutes from the date object.
     * @param currentDate
     * @return
     */
    public static Long getMinutes(Date currentDate){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(currentDate);
        //if(minutes==-1) minutes=60;
        return (long) calendar.get(Calendar.MINUTE);
    }

    /**
     * Convert Date Object to String
     * @param Date
     * @return
     */
    public static String ConvertDateToString(java.util.Date Date){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(Date.getTime());
    }


    public static Long [] getTimeLeft(Date insulinDoseDate, Date currentDate){
        if(insulinDoseDate.getTime()>=currentDate.getTime()) {
            Date sub = new Date(insulinDoseDate.getTime() - currentDate.getTime());
            Log.d("Diff", sub.toString());
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
            calendar.setTime(sub);
            Long time[] = new Long[2];
            time[0] = (long) calendar.get(Calendar.HOUR_OF_DAY);
            time[1] = (long) calendar.get(Calendar.MINUTE);
            return time;
        }
        Long time[] = new Long[2];
        time[0] = (long) 0;
        time[1] = (long) 0;
        return time;
    }
}
