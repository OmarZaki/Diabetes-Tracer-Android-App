package com.example.omar.diabetestracerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.omar.diabetestracerapp.data_model.User;

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
        ContentValues contentValues = user.getContentValuesObject();
        long i = database.insert(User._USER_TABLE,null,contentValues);
        if(i!= 0){
            Log.i("TAG", "User's record inserted!") ;

        }else {
            Log.i("TAG", "User's record is not inserted !");
        }
    }
    public void cleanTable(String tableName){
        long i = database.delete(tableName,null,null);
    }
    public User retrieveUserFromDataBase(){
        User user= null;
        Cursor cursor = database.query(User._USER_TABLE, User.USER_COLS,null, null,null,null,null);
        cursor.moveToFirst();
        Log.i("TTT", String.valueOf(cursor.getCount()));
        if(cursor.getCount()!= 0) {
            user = User.getUserFromCourser(cursor);
        }
        cursor.close();
        return user;
    }


}
