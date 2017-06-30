package com.example.marcus.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.EditText;
import java.net.URL;
import android.os.AsyncTask;
import com.example.marcus.newsapp.utilities.NetworkUtil;
import android.view.MenuItem;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

import android.net.Uri;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    //changes for github in class
    //2nd changes for github in class

    private TextView mNewsTextView;
    private ProgressBar progress;
    private EditText search;
    private RecyclerView recyclerView;
    //private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsTextView = (TextView) findViewById(R.id.news_data);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        search = (EditText) findViewById(R.id.searchQuery);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);

        loadNewsData();
    }

    private void loadNewsData(){ new FetchNewsTask().execute();}

    public class FetchNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>> implements NewsAdapter.ItemClickListener {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        public void openURL(String url){
            Uri parsePage = Uri.parse(url);

            Intent newsIntent = new Intent(Intent.ACTION_VIEW, parsePage);

            startActivity(newsIntent);
        }

        @Override
        public void onItemClick (int x){
            String url = newsData.get(x).getUrl();

            openURL(url);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(String... params) {
            URL newsRequestURL = NetworkUtil.buildURL();
            ArrayList<NewsItem> newsItem = null;
            try {
                String jsonNewsResponse = NetworkUtil.getResponseFromHttpUrl(newsRequestURL);
                newsItem = NetworkUtil.parseJSONFile(jsonNewsResponse);
                //return jsonNewsResponse;
            } catch (JSONException e){
                e.printStackTrace();
                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return newsItem;
        }

        ArrayList<NewsItem> newsData;
        @Override
        protected void onPostExecute(ArrayList<NewsItem> newsData){
            this.newsData = newsData;
            super.onPostExecute(newsData);

            progress.setVisibility(View.GONE);

            if(newsData != null){
                //mNewsTextView.append((newsData) + "/n/n/n");
                NewsAdapter newsAdapter = new NewsAdapter(this);

                newsAdapter.setNewsData(newsData);

                recyclerView.setAdapter(newsAdapter);
            }
            //progress.setVisibility(View.INVISIBLE);
        }
    }
}
