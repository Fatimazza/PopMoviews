package com.fatimazza.popmoviews.popmoviews.data;


import com.fatimazza.popmoviews.popmoviews.network.MovieDetailDao;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class FavoriteMoviesDbManager {

    public static void insertFavoriteMovie(Context context, MovieDetailDao movieDetailDao) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(
            FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_ID,
            movieDetailDao.getId());
        contentValues.put(
            FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_TITLE,
            movieDetailDao.getTitle());
        contentValues.put(
            FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_VOTE,
            movieDetailDao.getVote_average());
        contentValues.put(
            FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_POSTER,
            movieDetailDao.getPoster_path());
        contentValues.put(
            FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_RELEASE,
            movieDetailDao.getRelease_date());
        contentValues.put(
            FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_OVERVIEW,
            movieDetailDao.getOverview());

        Uri insertedUri = context.getContentResolver().insert(
            FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI, contentValues);

        if (insertedUri != null) {
            Log.d("Okhttp INSERT", insertedUri.toString());
        }
    }

    public static void deleteFavoriteMovie(Context context, String id) {

        Uri deleteUri = FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI;
        deleteUri = deleteUri.buildUpon().appendPath(id).build();


        int deleted = context.getContentResolver().delete(deleteUri, null, null);

        if (deleted > 0) {
            Log.d("Okhttp DELETE", +deleted +" row(s)");
        }
    }

}
