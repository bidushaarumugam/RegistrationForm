package com.example.user.AndRoy;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.AndRoy.data.MyPreferences;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    private RecyclerView mrecyclerview;
    private List<DisplayItem> displayitemList;
    private ProgressBar mprogressbar;
    private RecyclerViewAdapt mrecyclerviewAdapt;
    private SharedPreferenceNews msharedPreferenceNews;
    private FirebaseAuth mfirebathAuth;
    private FirebaseAuth.AuthStateListener mfirebathListener;
    private String urlNews = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=a65e2431ef9141ab93e78509b14554d0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mrecyclerview = (RecyclerView) findViewById(R.id.RecyclerNews);
        mprogressbar = (ProgressBar) findViewById(R.id.progresbar_News);
        mfirebathAuth = FirebaseAuth.getInstance();
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        getDisplay();
        initDisplay();
    }




    private class DownloadTask extends AsyncTask<String, Void, Integer>{
        @Override
        protected void onPreExecute() {
        mprogressbar.setVisibility(View.VISIBLE);


        }

        @Override
        protected Integer doInBackground(String... params) {
            int result=0;
            HttpURLConnection mhttpConnection;
            try {
                URL murl=new URL(params[0]);
                mhttpConnection=(HttpURLConnection)murl.openConnection();

                BufferedReader mBufferReader=new BufferedReader(new InputStreamReader(mhttpConnection.getInputStream()));
                StringBuilder mstringBuilder=new StringBuilder();
                String message;
                while ((message = mBufferReader.readLine()) != null){
                    mstringBuilder.append(message);
                    parseResult(mstringBuilder.toString());
                    result=1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }
    }

    private void parseResult(String result) {

        try {
            JSONObject mjsonObject=new JSONObject(urlNews);
            JSONArray articles=mjsonObject.optJSONArray("articles");
            displayitemList=new ArrayList<>();
            for(int i=0;i<articles.length();i++){
                JSONObject arc = articles.getJSONObject(i);
                DisplayItem mdisplayItem=new DisplayItem();
                mdisplayItem.setHeading(arc.getString("title"));
                mdisplayItem.setPictures(arc.getString("urlToImage"));
                displayitemList.add(mdisplayItem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getDisplay() {

        if(SharedPreferenceNews.init(getApplicationContext()).isJStringAvailable1()){
            Log.i(TAG,"getDisplay:StringAvailable");
            parseResult(SharedPreferenceNews.init(getApplicationContext()).getNewsString());
            setAdapt(1);
        }
        else if (isNetworkAvailable()) {
            new DownloadTask().execute(urlNews);
            Log.i(TAG, "getData: Network available");
        } else {
            Log.i(TAG, "getData: Network unavailable");
        }
    }

    private void setAdapt(int result) {
        mprogressbar.setVisibility(View.GONE);
        if(result==1){
            mrecyclerviewAdapt=new RecyclerViewAdapt(NewsActivity.this,displayitemList);
            mrecyclerview.setAdapter(mrecyclerviewAdapt);
        }
        else{
            Toast.makeText(NewsActivity.this, "Fail to fetch data. Check your connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void initDisplay() {

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NewsActivity.this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) return false;
        NetworkInfo.State networkState = networkInfo.getState();
        return (networkState == NetworkInfo.State.CONNECTED || networkState == NetworkInfo.State.CONNECTING);
    }
}
