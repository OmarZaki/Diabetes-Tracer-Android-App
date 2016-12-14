package com.example.omar.diabetestracerapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
     *
     */
    public void close(){
        dbHelper.close();
    }



}
