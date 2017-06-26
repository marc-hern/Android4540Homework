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

public class MainActivity extends AppCompatActivity {

    //changes for github in class
    //2nd changes for github in class

    private TextView mNewsTextView;
    private ProgressBar progress;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsTextView = (TextView) findViewById(R.id.news_data);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        search = (EditText) findViewById(R.id.searchQuery);
        loadNewsData();
    }

    private void loadNewsData(){ new FetchNewsTask().execute();}

    public class FetchNewsTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            URL newsRequestURL = NetworkUtil.buildURL();
            try {
                String jsonNewsResponse = NetworkUtil.getResponseFromHttpUrl(newsRequestURL);
                return jsonNewsResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String newsData){
            if(newsData != null){
                mNewsTextView.append((newsData) + "/n/n/n");
            }
            progress.setVisibility(View.INVISIBLE);
        }
    }
}
