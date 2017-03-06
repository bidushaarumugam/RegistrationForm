package com.example.user.AndRoy;

import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.user.AndRoy.data.NewsContract;
import com.example.user.AndRoy.network.NewsAsync;
import com.example.user.AndRoy.util.NewsCursorAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG ="TAG" ;
    private static final int NEWS_LOADER_ID = 5454;
    private ListView listView;
    private ProgressBar mprogressbar;
    private SharedPreferenceNews msharedPreferenceNews;
    private FirebaseAuth mfirebathAuth;
    NewsCursorAdapter newsCursorAdapter;
    private FirebaseAuth.AuthStateListener mfirebathListener;
    private String url = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=a65e2431ef9141ab93e78509b14554d0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView = (ListView) findViewById(R.id.lv_activity_news);
        mprogressbar = (ProgressBar) findViewById(R.id.progresbar_News);
        mfirebathAuth = FirebaseAuth.getInstance();
        newsCursorAdapter = new NewsCursorAdapter(NewsActivity.this, null);
        listView.setAdapter(newsCursorAdapter);
        loadData();
    }

    private void loadData() {
        new NewsAsync(getApplicationContext())
                .execute(url, NewsAsync.NEWS);
        getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(),
                NewsContract.News.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(TAG, "onLoadFinished: " + data.getCount());
        newsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        newsCursorAdapter.swapCursor(null);
    }
}
