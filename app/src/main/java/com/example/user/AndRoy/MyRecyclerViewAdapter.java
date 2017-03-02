package com.example.user.AndRoy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.load.model.ImageVideoModelLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.user.AndRoy.MyRecyclerViewAdapter.CustomHolder.imageView;
import static com.example.user.AndRoy.MyRecyclerViewAdapter.CustomHolder.textView;
import static com.example.user.AndRoy.R.id.image;
import static com.example.user.AndRoy.R.id.imageview;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomHolder> {

    private List<FeedItem> feedItemList;
    private Context mContext;
    private static String Title = "title";

    public MyRecyclerViewAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public MyRecyclerViewAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, null);
        CustomHolder viewHolder = new CustomHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, final int position) {

        final FeedItem feedItem = feedItemList.get(position);

        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Picasso.with(mContext).load(feedItem.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }

        CustomHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FeedItem feedItem = (FeedItem) v.getTag();
                Intent intent=new Intent(mContext,ImageActivity.class);
                intent.putExtra(Title, textView.getText().toString());
                mContext.startActivity(intent);
                v.setDrawingCacheEnabled(true);
                Bitmap b = imageView.getDrawingCache();


                intent.putExtra("Bitmap", b);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return (null!= feedItemList ? feedItemList.size() : 0);
    }

    static class CustomHolder extends RecyclerView.ViewHolder {

        static ImageView imageView;
        static TextView textView;
        static  String Title="Title";


        CustomHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            textView = (TextView) itemView.findViewById(R.id.title);


        }
    }


}
