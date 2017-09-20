package com.fatimazza.popmoviews.popmoviews;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;


public class MoviesJsonUtils {

    private static final String TAG = MoviesActivity.class.getSimpleName();

    public static String[] getSimpleMoviesStringFromJson(Context context, String moviesJsonString)
        throws JSONException {

        final String MD_STATUS_CODE = "status_code";

        final String MD_MESSAGE_CODE = "status_message";

        final String MD_MOVIE_ID = "id";

        final String MD_MOVIE_TITLE = "original_title";

        final String MD_MOVIE_VOTE = "vote_average";

        final String MD_MOVIE_RELEASE = "release_date";

        final String MD_MOVIE_POSTER = "poster_path";

        final String MD_MOVIE_OVERVIEW = "overview";

        String moviesData[] = null;

        JSONObject moviesDetailObject = new JSONObject(moviesJsonString);

        if (moviesDetailObject.has(MD_STATUS_CODE)) {
            String errorMessage = moviesDetailObject.getString(MD_MESSAGE_CODE);
            Log.d(TAG, "JsonMovieErr " + errorMessage);
            return null;
        }

        int id = moviesDetailObject.getInt(MD_MOVIE_ID);
        String title = moviesDetailObject.getString(MD_MOVIE_TITLE);
        int voteAverage = moviesDetailObject.getInt(MD_MOVIE_VOTE);
        String releaseDate = moviesDetailObject.getString(MD_MOVIE_RELEASE);
        String posterPath = moviesDetailObject.getString(MD_MOVIE_POSTER);
        String overview = moviesDetailObject.getString(MD_MOVIE_OVERVIEW);

        moviesData = new String[1];
        moviesData[0] = id + " " + title + " " +voteAverage;

        return moviesData;
    }

}
