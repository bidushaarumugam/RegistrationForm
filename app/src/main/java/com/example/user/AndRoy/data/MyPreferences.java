package com.example.user.AndRoy.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class MyPreferences {


    private SharedPreferences mSharedPreferences;

    private MyPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(PrefContract.Json.PREF_NAME, MODE_PRIVATE);
    }

    public static MyPreferences init(Context context) {
        return new MyPreferences(context);
    }

    public boolean isJStringAvailable() {
        //returns TRUE if JString is not null
        boolean availbilty = mSharedPreferences.getString(PrefContract.Json.JSTRING, null) != null;
        Log.i("TAG", "isJStringAvailable: " + availbilty);
        return availbilty;
    }

    public String getJString() {
        return mSharedPreferences.getString(PrefContract.Json.JSTRING, null);
    }

    public void setJString(String jString) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PrefContract.Json.JSTRING, jString);
        editor.apply();
    }
}


