package com.example.omar.diabetestracerapp.data_model;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by OMAR on 12/9/2016.
 */

public class User {
    //
     public final static String _USER_ONFO_PUT_EXTRA_STRING="UserInfoToLogIn";
    // Table name
    public final static String _USER_TABLE = "Users";

    // User Columns names
    public final static String _ID = "id";
    public final static String _FIRST_NAME = "fname";
    public final static String _LAST_NAME = "lname";
    public final static String _EMAIL = "email";
    public final static String _PASSWORD = "password";
    public final static String _PHONE_NUMBER = "phone_number";
    public final static String _TOKEN = "token";
    public final static String _ADMIN = "admin";
    public final static String _TYPE = "type";
    public final static String _CREATION_DATE = "creation_date";
    public final static String _BIRTH_DATE= "birth_date";
    public final static String _ADDRESS = "address";

    public static String[] USER_COLS = {User._FIRST_NAME,
            User._LAST_NAME,
            User._EMAIL,
            User._PASSWORD,
            User._PHONE_NUMBER,
            User._TOKEN,
            User._TYPE,
            User._CREATION_DATE,
            User._BIRTH_DATE,
            User._ADDRESS};
    /**
     * User's fields
     */
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Boolean admin;
    private Boolean type;
    private String token;
    private Date creationDate;
    private String address;
    private java.util.Date birthDate;


    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(java.util.Date birthDate) {
//        this.birthDate =new java.sql.Date(birthDate.getTime());
        this.birthDate=birthDate;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.util.Date creationDate) {
//        this.creationDate = new java.sql.Date(creationDate.getTime());
            this.creationDate= creationDate;
    }

    /**
     * OMAR: To validate the user fields, You can add more rule as its needed.
     *
     * @return
     */
    public Boolean validate() {
        Boolean result = false;
        if (this.firstName != "" && this.lastName != "" && validateEmailFormat(this.email) && this.password != "") {
            result = true;
        }
        return result;
    }

    /**
     * To validate the email address i used JavaMail package . see more
     * https://mvnrepository.com/artifact/javax.mail/mail/1.4 The pakacke in
     * Javax.mail.jar
     *
     * @param email
     * @return
     */
    public static Boolean validateEmailFormat(String email) {
        Boolean result = true;
        try {

            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();

        } catch (AddressException ex) {
            result = false;
        }

        return result;
    }
    /**
     * Convert date as string to Java.util.Date Object
     *
     * @param date
     * @return
     */
    public static java.util.Date ConvertStringToDateObjectFormDB(String date) {
        java.util.Date parsedDate = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");
            parsedDate = df.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }
    /**
     * Convert date as string to Java.util.Date Object
     *
     * @param date
     * @return
     */
    public static java.util.Date ConvertStringToDateObject(String date) {
        java.util.Date convertedDate = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
             convertedDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
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

    /**
     * Convert day, month and year (Integers) to Calender object
     * @param day
     * @param month
     * @param year
     * @return
     */
    public static Calendar ConvertIntegersToCalenderObject(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }

    /**
     * Convert to Json object
     * @param user
     * @return
     */
    public static org.json.JSONObject toJsonObject(User user){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(convertUserToJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Convert user Java Object to String
     * @param object
     * @return
     */
    public static String convertUserToJson(User object){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
                .create();
        return gson.toJson(object);
    }
    /**
     * Convert to User Json String to User Object.
     * @param jsonObject
     * @return
     */
    public static User convertJsonToUser(String jsonObject) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
                .create();
        User user = gson.fromJson(jsonObject, User.class);
        return user;
    }
    public ContentValues getContentValuesObject(){
        ContentValues userFieldValues = new ContentValues();
        userFieldValues.put(User._ID, this.getId());
        userFieldValues.put(User._FIRST_NAME, this.getFirstName());
        userFieldValues.put(User._LAST_NAME, this.getLastName());
        userFieldValues.put(User._EMAIL, this.getEmail());
        userFieldValues.put(User._ADDRESS, this.getAddress());
        userFieldValues.put(User._BIRTH_DATE, this.getBirthDate().toString());
        userFieldValues.put(User._PASSWORD, this.getPassword());
        userFieldValues.put(User._PHONE_NUMBER, this.getPhoneNumber());
        userFieldValues.put(User._TYPE, this.getType());
        userFieldValues.put(User._TOKEN, this.getToken());
        userFieldValues.put(User._CREATION_DATE, this.getCreationDate().toString());

        return userFieldValues;
    }

    /**
     * get Data from Cursor Object
     * @param cursor
     * @return
     */
    public static User getUserFromCourser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setFirstName(cursor.getString(cursor.getColumnIndex(User._FIRST_NAME)));
        user.setLastName(cursor.getString(cursor.getColumnIndex(User._LAST_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(User._EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(User._PASSWORD)));
        user.setAddress(cursor.getString(cursor.getColumnIndex(User._ADDRESS)));
        user.setBirthDate(new Date(User.ConvertStringToDateObjectFormDB(cursor.getString(cursor.getColumnIndex(User._BIRTH_DATE))).getTime()));
        user.setPhoneNumber(cursor.getString(cursor.getColumnIndex(User._PHONE_NUMBER)));
        user.setType(cursor.getInt(cursor.getColumnIndex(User._TYPE))>0);
        user.setToken(cursor.getString(cursor.getColumnIndex(User._TOKEN)));
        user.setCreationDate(User.ConvertStringToDateObjectFormDB(cursor.getString(cursor.getColumnIndex(User._CREATION_DATE))));
        return user;
    }

}
