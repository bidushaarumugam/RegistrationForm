package com.example.user.AndRoy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    //private String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=70";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView4);
        textView.setText(getIntent().getStringExtra("title"));
        Glide.with(getApplicationContext())
                .load(getIntent().getStringExtra("imageUrl"))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_function, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_function_news:
               Intent intent1=new Intent(ImageActivity.this,NewsActivity.class);
                startActivity(intent1);
                break;
            case R.id.video_listener:
                Intent intent=new Intent(ImageActivity.this,LoadActivity.class);
                startActivity(intent);
                break;
           /* case R.id.back:
                onBackPressed();
                break;*/
            default:
                onBackPressed();
        }
        return true;
    }

}