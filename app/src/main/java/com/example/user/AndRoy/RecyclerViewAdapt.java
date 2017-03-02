package com.example.user.AndRoy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.user.AndRoy.RecyclerViewAdapt.CustomHolder.pict;

/**
 * Created by user on 1/3/17.
 */

public class RecyclerViewAdapt extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomHolder> {
    private List<DisplayItem> displayitemList;
    private Context mcontext;

    public RecyclerViewAdapt(Context context, List<DisplayItem> displayitemList) {
        this.displayitemList = displayitemList;
        this.mcontext = context;
    }

    @Override
    public MyRecyclerViewAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listnews_layout,null);
        MyRecyclerViewAdapter.CustomHolder viewHolder = new MyRecyclerViewAdapter.CustomHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.CustomHolder holder, int position) {
        //final DisplayItem mdisplayItem= displayitemList.get(position);
        final DisplayItem mdisplayItem=displayitemList.get(position);
        if(!TextUtils.isEmpty(mdisplayItem.getPictures())){
            Picasso.with(mcontext).load(mdisplayItem.getPictures())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(pict);
        }
        CustomHolder.head.setText(Html.fromHtml(mdisplayItem.getHeading()));

    }

    @Override
    public int getItemCount() {
        return (null!= displayitemList ? displayitemList.size() : 0);
    }

     static class CustomHolder extends RecyclerView.ViewHolder {
        static ImageView pict;
        static TextView head;

        public CustomHolder(View itemView) {
            super(itemView);
            pict=(ImageView)itemView.findViewById(R.id.imageView2);
            head=(TextView)itemView.findViewById(R.id.textView);
        }
    }
}
