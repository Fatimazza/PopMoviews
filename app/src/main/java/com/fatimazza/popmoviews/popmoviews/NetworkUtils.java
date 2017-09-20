package com.fatimazza.popmoviews.popmoviews;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/";

    final static String API_KEY_PARAM = "api_key";

    final static String LANGUAGE_PARAM = "language";

    private static final String apiKey = "";

    private static final String language = "en-US";

    public static URL buildURL(String movieId) {

        Uri builtURI = Uri.parse(MOVIES_BASE_URL).buildUpon()
            .appendPath(movieId)
            .appendQueryParameter(API_KEY_PARAM, apiKey)
            .appendQueryParameter(LANGUAGE_PARAM, language)
            .build();

        URL url = null;
        try {
            url = new URL(builtURI.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);
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
