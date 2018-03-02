package com.fatimazza.popmoviews.popmoviews.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fatimazza.popmoviews.popmoviews.data.FavoriteMoviesContract.*;

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "popmoviews.db";
    private static final int DATABASE_VERSION = 1;

    public FavoriteMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVMOVIES_TABLE =
            "CREATE TABLE " + FavoriteMoviesEntry.TABLE_NAME + " (" +
                FavoriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteMoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                FavoriteMoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavoriteMoviesEntry.COLUMN_MOVIE_VOTE + " TEXT, " +
                FavoriteMoviesEntry.COLUMN_MOVIE_POSTER + " TEXT, " +
                FavoriteMoviesEntry.COLUMN_MOVIE_OVERVIEW + " TEXT " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVMOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
