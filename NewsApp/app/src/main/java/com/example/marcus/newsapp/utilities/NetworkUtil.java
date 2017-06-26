package com.example.marcus.newsapp.utilities;

/**
 * Created by Marcus on 6/25/2017.
 */

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.util.Log;

public class NetworkUtil {
    public static final String TAG = "NetworkUtils";

    private static final String NEWS_BASE_URL = "https://newsapi.org/v1/articles?";
    final static String SOURCE_PARAM = "source";
    final static String SORTBY_PARAM = "sortBy";
    final static String APIKEY_PARAM = "apiKey";

    private static final String source = "the-next-web";
    private static final String sortBy = "latest";
    private static final String apiKey = "8ec7ea91ac5544748c71b271cb8b19e3";

    public static URL buildURL(){
        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAM, source)
                .appendQueryParameter(SORTBY_PARAM, sortBy)
                .appendQueryParameter(APIKEY_PARAM, apiKey)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Build URL" + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
