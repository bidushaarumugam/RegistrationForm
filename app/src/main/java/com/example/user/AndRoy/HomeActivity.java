package com.example.user.AndRoy;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.AndRoy.data.MyPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements MyRecyclerViewAdapter.OnItemClickListener {
    private static final String TAG = "TAG";
    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=70";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mfirebaseAuth=FirebaseAuth.getInstance();
        initListener();
        progressBar = (ProgressBar) findViewById(R.id.pgbar_home);
        getData();
    }


    public void getData() {
        if (MyPreferences.init(getApplicationContext()).isJStringAvailable()) {
            Log.i(TAG, "getData: Jstring available");
            parseResult(MyPreferences.init(getApplicationContext()).getJString());
            setAdapter(1);
        } else if (isNetworkAvailable()) {
            new DownloadTask().execute(url);
            Log.i(TAG, "getData: Network available");
        } else {
            Log.i(TAG, "getData: Network unavailable");
        }
    }

    @Override
    public void OnItemClick(View view, FeedItem feedItem) {
        Intent intent=new Intent(HomeActivity.this,ImageActivity.class);
        intent.putExtra("title", feedItem.getTitle());
        intent.putExtra("imageUrl", feedItem.getThumbnail());
        startActivity(intent);
    }


    private class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {

            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    MyPreferences.init(HomeActivity.this).setJString(response.toString());
                    parseResult(response.toString());
                    result = 1;
                } else {
                    result = 0;
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }
        @Override
        protected void onPostExecute(Integer result) {
            setAdapter(result);
        }
    }

    private void setAdapter(int result) {
        progressBar.setVisibility(View.GONE);

        if (result == 1) {
            adapter = new MyRecyclerViewAdapter(getApplicationContext(), feedsList, this);
            mRecyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(HomeActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
        }
    }


    private void parseResult(final String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            feedsList = new ArrayList<>();
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(post.optString("title"));
                item.setThumbnail(post.optString("thumbnail"));
                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser == null) {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    HomeActivity.this.finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuthStateListener != null) {
            mfirebaseAuth.addAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mfirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_main_reset:
                resetPass();
                break;
            case R.id.menu_main_loggout:
                logoutUser();
                break;
            default:
        }
        return true;
    }

    private void logoutUser() {
        FirebaseUser mFireuser = mfirebaseAuth.getCurrentUser();
        if (mFireuser != null) {
            mfirebaseAuth.signOut();
        }
    }

    private void resetPass() {
        FirebaseUser mFireuser = mfirebaseAuth.getCurrentUser();
        if (mFireuser != null) {
            String email = mFireuser.getEmail();
            assert email != null;
            mfirebaseAuth.sendPasswordResetEmail(email);
            Toast.makeText(this, "Reset email has been sent to your registered email address", Toast.LENGTH_SHORT).show();
            logoutUser();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) HomeActivity.this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) return false;
        NetworkInfo.State networkState = networkInfo.getState();
        return (networkState == NetworkInfo.State.CONNECTED || networkState == NetworkInfo.State.CONNECTING);
    }
}

