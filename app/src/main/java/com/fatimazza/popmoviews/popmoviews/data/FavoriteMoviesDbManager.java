package com.fatimazza.popmoviews.popmoviews.data;


import com.fatimazza.popmoviews.popmoviews.network.MovieDetailDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public static List<MovieDetailDao> readFavoriteMovie(Cursor cursor) {
        List<MovieDetailDao> favoriteMovies = new ArrayList<>();

        long id = -1;

        try {

            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(
                        FavoriteMoviesContract.FavoriteMoviesEntry._ID));
                    String movieId = cursor.getString(cursor.getColumnIndexOrThrow(
                        FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_ID));
                    String movieTitle = cursor.getString(cursor.getColumnIndexOrThrow(
                        FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_TITLE));
                    String movieVote = cursor.getString(cursor.getColumnIndexOrThrow(
                        FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_VOTE));
                    String moviePosterPath = cursor.getString(cursor.getColumnIndexOrThrow(
                        FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_POSTER));
                    String movieReleaseDate = cursor.getString(cursor.getColumnIndexOrThrow(
                        FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_RELEASE));
                    String movieOverview = cursor.getString(cursor.getColumnIndexOrThrow(
                        FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_OVERVIEW));

                    MovieDetailDao movieDetail = new MovieDetailDao();
                    movieDetail.setId(Long.parseLong(movieId));
                    movieDetail.setTitle(movieTitle);
                    movieDetail.setVote_average(Double.parseDouble(movieVote));
                    movieDetail.setPoster_path(moviePosterPath);
                    movieDetail.setRelease_date(movieReleaseDate);
                    movieDetail.setOverview(movieOverview);
                    favoriteMovies.add(movieDetail);

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DBManager Error : ", e.toString());
        }
        return favoriteMovies;
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
