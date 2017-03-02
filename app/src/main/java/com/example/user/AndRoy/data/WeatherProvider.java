/**
 * Copyright will updated soon
 */

package com.example.user.AndRoy.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class WeatherProvider extends ContentProvider {

    private WeatherDbHelper mWeatherDbHelper;

    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WEATHER_CODE = 101;
    private static final int WEATHER_ID_CODE = 102;
    private static final int USER_CODE = 103;
    private static final int USER_ID_CODE = 104;

    static {
        mUriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY, WeatherContract.CONTENT_WEATHER, WEATHER_CODE);
        mUriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY, WeatherContract.CONTENT_WEATHER_ID, WEATHER_ID_CODE);
        mUriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY,WeatherContract.CONTENT_USERS, USER_CODE);
        mUriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY, WeatherContract.CONTENT_USERS_ID, USER_ID_CODE);
    }

    public WeatherProvider() {
    }

    @Override
    public boolean onCreate() {
        mWeatherDbHelper = new WeatherDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase mSQLiteDatabase = mWeatherDbHelper.getReadableDatabase();

        if (mUriMatcher.match(WeatherContract.WEATHER_URI) == WEATHER_CODE ) {
            return mSQLiteDatabase.query(
                    WeatherContract.LocationEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    sortOrder,
                    null);

        } else if (mUriMatcher.match(WeatherContract.WEATHER_ID_URI) == WEATHER_ID_CODE) {
            return mSQLiteDatabase.query(
                    WeatherContract.LocationEntry.TABLE_NAME,
                    null,
                    selection + "=?",
                    selectionArgs,
                    null,
                    null,
                    null,
                    null);

        }  else if (mUriMatcher.match(WeatherContract.USER_URI) == USER_CODE) {
            return mSQLiteDatabase.query(
                    WeatherContract.Users.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    sortOrder,
                    null);

        }  else if (mUriMatcher.match(WeatherContract.USER_ID_URI) == USER_ID_CODE) {
            String selectionArgs_id[] = new String[] {String.valueOf(ContentUris.parseId(uri ))};
            return mSQLiteDatabase.query(
                    WeatherContract.Users.TABLE_NAME,
                    null,
                    WeatherContract.Users.COLUMN_EMAIL + "=?",
                    selectionArgs_id,
                    null,
                    null,
                    sortOrder,
                    null);

        } else throw new IllegalArgumentException("query: Uri match not found. " + uri);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase mWritableDatabase = mWeatherDbHelper.getWritableDatabase();

        if (mUriMatcher.match(uri) == WEATHER_CODE) {
            mWritableDatabase.insert(
                    WeatherContract.LocationEntry.TABLE_NAME,
                    null,
                    values);

        } else if (mUriMatcher.match(uri) == USER_CODE) {
            mWritableDatabase.insert(
                    WeatherContract.Users.TABLE_NAME,
                    null,
                    values);

        } else throw new IllegalArgumentException("insert: Uri match not found. " + uri);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase mWritableDatabase = mWeatherDbHelper.getWritableDatabase();

        if (mUriMatcher.match(uri) == WEATHER_CODE) {
            return mWritableDatabase.delete(
                    WeatherContract.LocationEntry.TABLE_NAME,
                    selection + "=?",
                    selectionArgs);

        } else if (mUriMatcher.match(uri) == WEATHER_ID_CODE) {
            return mWritableDatabase.delete(
                    WeatherContract.LocationEntry.TABLE_NAME,
                    selection + "=?",
                    selectionArgs);

        } else if (mUriMatcher.match(uri) == USER_CODE) {
            return mWritableDatabase.delete(
                    WeatherContract.Users.TABLE_NAME,
                    selection + "=?",
                    selectionArgs);

        } else throw new IllegalArgumentException("insert: Uri match not found. " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase mWritableDatabase = mWeatherDbHelper.getWritableDatabase();

        if (mUriMatcher.match(uri) == WEATHER_CODE) {
            return mWritableDatabase.update(
                    WeatherContract.LocationEntry.TABLE_NAME,
                    values,
                    selection + "=?",
                    selectionArgs);

        } else if (mUriMatcher.match(uri) == WEATHER_ID_CODE) {
            return mWritableDatabase.update(
                    WeatherContract.LocationEntry.TABLE_NAME,
                    values,
                    selection + "=?",
                    selectionArgs);

        } else if (mUriMatcher.match(uri) == USER_CODE) {
            return mWritableDatabase.update(
                    WeatherContract.Users.TABLE_NAME,
                    values,
                    selection + "=?",
                    selectionArgs);

        } else throw new IllegalArgumentException("insert: Uri match not found. " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "text/plain";
    }
}
