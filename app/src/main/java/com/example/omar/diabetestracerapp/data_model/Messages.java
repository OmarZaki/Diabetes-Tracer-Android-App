package com.example.omar.diabetestracerapp.data_model;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by OMAR on 12/9/2016.
 */
public class Messages {

    public final static String _MESSAGES_TABLE = "Messages";

    // Messages Columns names
    public final static String _ID = "id";
    public final static String _TEXT = "text";
    public final static String _DATE_TIME = "date_time";
    public final static String _USERS_ID = "Users_id";
    public static String[] _MESSAGES_COLS = {Messages._ID,
            Messages._TEXT,
            Messages._DATE_TIME,
            Messages._USERS_ID};
    public static final String  _MESSAGE_TITLE="Message ";
    private int id;
    private String text;
    private Date date_time;
    private int Users_id;

    public ContentValues getContentValuesObject() {

        ContentValues messageFieldValues = new ContentValues();
        messageFieldValues.put(Messages._ID, this.getId());
        messageFieldValues.put(Messages._TEXT, this.getText());
        messageFieldValues.put(Messages._DATE_TIME, this.getDate_time().toString());
        messageFieldValues.put(Messages._USERS_ID, this.getUsers_id());
        return messageFieldValues;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public boolean validate() {
        if (this.getDate_time() != null && !this.getText().equals("") && this.getUsers_id() > 0)
            return true;
        return false;
    }

    public static Messages getMessageObjectFromCursor(Cursor cursor) {
        Messages messages = new Messages();
        messages.setId(cursor.getInt(cursor.getColumnIndex(Messages._ID)));
        messages.setText(cursor.getString(cursor.getColumnIndex(Messages._TEXT)));
        messages.setUsers_id(cursor.getInt(cursor.getColumnIndex(Messages._USERS_ID)));
        messages.setDate_time(new Date(Messages.convertStringToDateObjectFormDB(cursor.getString(cursor.getColumnIndex(Messages._DATE_TIME))).getTime()));
        return messages;
    }


    public static java.util.Date convertStringToDateObjectFormDB(String date) {
        java.util.Date parsedDate = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");
            parsedDate = df.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    public static JSONObject toJsonObject(Messages message) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(convertUserToJson(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static String convertUserToJson(Messages message) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.S")
                .create();
        return gson.toJson(message);
    }

    public static Messages convertJsonToMessages(String response) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.S")
                .create();
        Messages message = gson.fromJson(response, Messages.class);
        return message;
    }
    public static ArrayList<Messages> convertJsonToList(String messages) {
        Gson gson =new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        ArrayList<Messages> messagesList = gson.fromJson(messages, new TypeToken<ArrayList<Messages>>(){}.getType());
        return messagesList;
    }
}
