package com.example.user.AndRoy.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.AndRoy.NewsImageActivity;
import com.example.user.AndRoy.R;
import com.example.user.AndRoy.data.NewsContract;


public class NewsCursorAdapter extends CursorAdapter {
    private Cursor cursor;

    public NewsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        cursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listnews_layout, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        Log.i("TAG", "bindView: " + cursor.getPosition());
        ImageView imageView = (ImageView) view.findViewById(R.id.img_item_news_image);
        TextView textView = (TextView) view.findViewById(R.id.tv_item_news_headline);
        int columnImgUrl = cursor.getColumnIndexOrThrow(NewsContract.News.COLUMN_URL_TO_IMAGE);
        int columnTitle = cursor.getColumnIndexOrThrow(NewsContract.News.COLUMN_TITLE);

        final String imageUrl = cursor.getString(columnImgUrl);
        final String newsHeading = cursor.getString(columnTitle);

        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
        textView.setText(newsHeading);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity=new Intent(mContext, NewsImageActivity.class);
                nextActivity.putExtra("imageUrl", imageUrl);
                nextActivity.putExtra("newsHeading", newsHeading);
                mContext.startActivity(nextActivity);
            }
        });
    }
}
