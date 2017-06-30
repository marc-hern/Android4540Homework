package com.example.marcus.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import com.example.marcus.newsapp.NewsItem;
import java.util.ArrayList;

/**
 * Created by Marcus on 6/26/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {

    private ArrayList<NewsItem> newsItem;

    ItemClickListener clickListener;

    public interface ItemClickListener {

        void onItemClick(int clickedItemIndex);
    }

    public NewsAdapter(ItemClickListener clickListener){

        this.clickListener = clickListener;
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, description, time;
        public void bind (int x){
            NewsItem holderItem = newsItem.get(x);

            title.setText(holderItem.getTitle());
            description.setText(holderItem.getDescription());
            time.setText(holderItem.getPublishedAt());
        }

        @Override
        public void onClick(View x){
            int position = getAdapterPosition();

            clickListener.onItemClick(position);
        }

        ItemHolder (View x){
            super(x);

            title = (TextView)x.findViewById(R.id.title);
            description = (TextView)x.findViewById(R.id.description);
            time = (TextView)x.findViewById(R.id.time);
        }
    }

    public void setNewsData(ArrayList<NewsItem> newsItem) {
        this.newsItem = newsItem;
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(R.layout.newsitemlayout, parent, shouldAttachToParentImmediately);

        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int x) {
        holder.bind(x);
    }

    @Override
    public int getItemCount() {

        return newsItem.size();
    }
}
