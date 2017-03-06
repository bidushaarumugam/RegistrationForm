package com.example.user.AndRoy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomHolder> {

    private List<FeedItem> feedItemList;
    private Context mContext;
    private static String Title = "title";
    private OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<FeedItem> feedItemList, OnItemClickListener onItemClickListener) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyRecyclerViewAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, null);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, final int position) {
        final FeedItem feedItem = feedItemList.get(position);

        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Glide.with(mContext)
                    .load(feedItem.getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(holder.imageView);
        }

        holder.textView.setText(feedItem.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v, feedItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null!= feedItemList ? feedItemList.size() : 0);
    }

    static class CustomHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public   String Title="Title";

        CustomHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            textView = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, FeedItem feedItem);
    }
}
