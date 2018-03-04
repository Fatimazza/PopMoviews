package com.fatimazza.popmoviews.popmoviews.data;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fatimazza.popmoviews.popmoviews.data.FavoriteMoviesContract.*;

public class FavoriteMoviesContentProvider extends ContentProvider {

    private FavoriteMoviesDbHelper favoriteMoviesDbHelper;

    public static final int FAVMOVIES = 100;
    public static final int FAVMOVIES_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY,
            FavoriteMoviesContract.PATH_FAV_MOVIES, FAVMOVIES);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY,
            FavoriteMoviesContract.PATH_FAV_MOVIES + "/#", FAVMOVIES_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        favoriteMoviesDbHelper = new FavoriteMoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
        @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
        @Nullable String[] strings) {
        return 0;
    }
}
