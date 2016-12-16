package com.example.omar.diabetestracerapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by OMAR on 12/14/2016.
 */

public class SqlHelper extends SQLiteOpenHelper {
    // The database v, if you change the database model , you need to update the database .
    public static final int  DATABASE_VERSION= 1;
    public static final String DATABASE_NAME="diabetes.db"; // database file name.
    /**
     * Constructor of the SQLHelper
     * @param context
     */
    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SqlContacts.STATEMENT_CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

}
