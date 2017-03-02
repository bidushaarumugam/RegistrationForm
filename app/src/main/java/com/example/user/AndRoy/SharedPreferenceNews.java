package com.example.user.AndRoy;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.user.AndRoy.data.MyPreferences;
import com.example.user.AndRoy.data.PrefContract;

/**
 * Created by user on 1/3/17.
 */

public class SharedPreferenceNews {
    SharedPreferences msharedPreferences;


    private SharedPreferenceNews(Context context){
            msharedPreferences=context.getSharedPreferences(NewsFunction.jsonnews.NEWSLAUCH,Context.MODE_PRIVATE);

    }
    public static SharedPreferenceNews init(Context context){
        return new SharedPreferenceNews(context);
    }
    public boolean isJStringAvailable1() {
        boolean availbilty = msharedPreferences.getString(NewsFunction.jsonnews.NEWSSTRING, null) != null;
        Log.i("TAG", "isJStringAvailable: " + availbilty);
        return availbilty;
    }
    public String getNewsString() {
        return msharedPreferences.getString(NewsFunction.jsonnews.NEWSSTRING, null);
    }
    public void setNewsString(String jString) {
        SharedPreferences.Editor editor = msharedPreferences.edit();
        editor.putString(NewsFunction.jsonnews.NEWSSTRING, jString);
        editor.apply();
    }
}
