package com.example.user.AndRoy.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAsync extends AsyncTask<String, Void, String> {
    private static final String TAG = "TAG";
    private NewsOnTaskFinishListener listener;

    public MyAsync(NewsOnTaskFinishListener listener) {
        this.listener = listener;
    }

    protected String doInBackground(String... params) {
        return doNetworkOperation(params[0]);
    }

    private String doNetworkOperation(String params) {
        HttpURLConnection mURLConnection;
        StringBuilder mStringBuilder;

        try {
            URL url = new URL(params);
            mURLConnection = (HttpURLConnection) url.openConnection();
            mURLConnection.setRequestMethod("GET");
            mURLConnection.setConnectTimeout(10000);
            mURLConnection.setReadTimeout(30000);

            BufferedReader mBufferedReader = new BufferedReader(
                    new InputStreamReader(mURLConnection.getInputStream()));

            String temp;
            mStringBuilder = new StringBuilder();
            while ((temp = mBufferedReader.readLine()) != null) {
                mStringBuilder.append(temp);
            }
            Log.i(TAG, "doNetworkOperation: " + mStringBuilder.toString());
            return mStringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "doNetworkOperation: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onTaskFinish(s);
    }
}
