package com.example.omar.diabetestracerapp.data_model;


import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by OMAR on 12/9/2016.
 */
public class InsulinDose {

    public final static String _InsulinDose_TABLE = "insulinDose";
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

    public int getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(int original_id) {
        this.original_id = original_id;
    }

    public static InsulinDose getInsulinDoseObject(Cursor cursor) {
        InsulinDose insulinDose = new InsulinDose();
        insulinDose.setId(cursor.getInt(cursor.getColumnIndex(InsulinDose._ID)));
        insulinDose.setQuantity(cursor.getInt(cursor.getColumnIndex(InsulinDose._QUANTITY)));
        insulinDose.setTaken(cursor.getInt(cursor.getColumnIndex(InsulinDose._TAKEN))>0);
        insulinDose.setOriginal_id(cursor.getInt(cursor.getColumnIndex(InsulinDose._ORIGANL_ID)));
        insulinDose.setDate_time(new Date(cursor.getLong(cursor.getColumnIndex(InsulinDose._DATE_TIME))));
        return insulinDose;
    }
    public static String getDateFromDateTimeObejct(Date dateObj){

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
                .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
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
                .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
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

}
