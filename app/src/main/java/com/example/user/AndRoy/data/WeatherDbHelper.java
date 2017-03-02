/**
 * Copyright will updated soon
 */

package com.example.user.AndRoy.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 *
 * This class creates SQLite database with help of WeatherContract.java file.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "WeatherDB.db";


    /**
     *
     * @param context Application context is passed for reference.
     */
    public WeatherDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    /**
     *
     * @param db Reference to the SQLiteDatabase object
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WeatherContract.Users.SQL_CREATE_USERS);
        db.execSQL(WeatherContract.LocationEntry.SQL_CREATE_ENTRIES);
    }

    /**
     *
     * @param db Reference to the SQLiteDatabase object
     * @param oldVersion Version id of older version
     * @param newVersion Version if of Newer version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db != null) {
            db.execSQL(WeatherContract.Users.SQL_DELETE_USERS);
            db.execSQL(WeatherContract.LocationEntry.SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
