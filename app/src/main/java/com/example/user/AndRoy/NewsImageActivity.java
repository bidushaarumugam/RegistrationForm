package com.example.user.AndRoy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class NewsImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_image);
        ImageView newsImage = (ImageView) findViewById(R.id.news_Imageview);
        TextView newsText = (TextView) findViewById(R.id.news_text);
        Intent i=getIntent();

        Glide.with(this)
                .load(i.getStringExtra("imageUrl"))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(newsImage);
        String newsTitle=i.getStringExtra("newsHeading");
        newsText.setText(newsTitle);
    }
}
