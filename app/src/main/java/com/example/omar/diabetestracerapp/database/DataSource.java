package com.example.omar.diabetestracerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.omar.diabetestracerapp.data_model.InsulinDose;
import com.example.omar.diabetestracerapp.data_model.Meal;
import com.example.omar.diabetestracerapp.data_model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by OMAR on 12/14/2016.
 */

public class DataSource {

    // database fields
    private SQLiteDatabase database;
    private SqlHelper dbHelper;

    /**
     * Constructor
     * @param context
     */
    public DataSource(Context context){
        dbHelper = new SqlHelper(context);

    }

    /**
     * Open the database
     */
    public void open(){
        this.database= dbHelper.getWritableDatabase();
    }

    /**
     * Close database
     */
    public void close(){
        dbHelper.close();
    }

    /**

     * Insert the User object into the database
     * @param user
     */
    public void insertUserToDataBase(User user){
        open();
        ContentValues contentValues = user.getContentValuesObject();
        long i = database.insert(User._USER_TABLE,null,contentValues);
        if(i!= 0){
            Log.i("TAG", "User's record inserted!") ;

        }else {
            Log.i("TAG", "User's record is not inserted !");
        }
        close();
    }

    /**
     * Clean all User table records
     * @param
     */
    public void cleanTables(){
        open();
        long i = database.delete(User._USER_TABLE,null,null);
        long i2 = database.delete(InsulinDose._InsulinDose_TABLE,null,null);
        close();
    }
    public void cleanTable(String tableName){
        open();
        long i = database.delete(tableName,null,null);
        close();
    }
    public void deleteDatabase(Context context){
        context.deleteDatabase(SqlHelper.DATABASE_NAME);
        Log.i("DATABASE", "Database has been erased !");
    }

    /**
     * get the User from database
     * @return
     */
    public User retrieveUserFromDataBase(){
        open();
        User user= null;
        Cursor cursor = database.query(User._USER_TABLE, User.USER_COLS,null, null,null,null,null);
        cursor.moveToFirst();
        Log.i("TTT", String.valueOf(cursor.getCount()));
        if(cursor.getCount()!= 0) {
            user = User.getUserFromCourser(cursor);
        }
        cursor.close();
        close();
        return user;
    }

    /**
     * Insert the list of insulin dose from database;
     * @param insulinDoses
     */
    public void insertListOfInsulinDoses(List<InsulinDose> insulinDoses){
        open();
        for (InsulinDose insulin :insulinDoses) {
            ContentValues insulinContentValues= insulin.getContentValuesObject();
            long i = database.insert(InsulinDose._InsulinDose_TABLE,null,insulinContentValues);
            if(i!= 0){
                Log.i("TAG", "User's record inserted!") ;

            }else {
                Log.i("TAG", "User's record is not inserted !");
            }
        }
        close();
    }

    /**
     * Update the current insulin dose;
     * @param insulinDose
     */
    public void updateInsulinDoseRecord(InsulinDose insulinDose){
        open();
        ContentValues cv = insulinDose.getContentValuesObject();
        long i = database.update(InsulinDose._InsulinDose_TABLE,cv,InsulinDose._ID+"="+insulinDose.getId(),null);
        if(i!= 0){
            Log.i("TAG", "Insulin's record is inserted!") ;

        }else {
            Log.i("TAG", "Insulin's record is not inserted !");
        }
        open();
    }

    /**
     * get current insulin dose ;
     * @param date
     * @return
     */
    public InsulinDose getCurrentInsulinDose(Date date){

        //TODO 1. Get All records from dataBase;
        //TODO 2. Extract the current date record ;

        open();
        InsulinDose insulinDose=null;
        Cursor cursor = database.query(InsulinDose._InsulinDose_TABLE, InsulinDose._INSULIN_COLS,null, null,null,null,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            insulinDose = InsulinDose.getInsulinDoseObject(cursor);
            String currentDate = InsulinDose.getDateFromDateTimeObejct(date);
            String insulinDoseDate= InsulinDose.getDateFromDateTimeObejct(insulinDose.getDate_time());
            if(currentDate.equals(insulinDoseDate)){
                return insulinDose;
            }
        }
        cursor.close();
        close();
        return insulinDose;
    }

    /**

     * Insert the Meal object into the database
     * @param meal
     */
    public void insertMealToDataBase(Meal meal){
        open();
        ContentValues contentValues = meal.getContentValuesObject();
        long i = database.insert(Meal._Meal_TABLE,null,contentValues);
        if(i!= 0){
            Log.i("TAG", "Meal's record inserted!") ;

        }else {
            Log.i("TAG", "Meal's record is not inserted !");
        }
        close();
    }


}
