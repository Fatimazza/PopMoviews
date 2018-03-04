package com.fatimazza.popmoviews.popmoviews.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fatimazza.popmoviews.popmoviews.data.FavoriteMoviesContract.*;

import static com.fatimazza.popmoviews.popmoviews.data.FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_NAME;

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
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        final SQLiteDatabase db = favoriteMoviesDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FAVMOVIES:
                long id = db.insert(TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FavoriteMoviesEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        final  SQLiteDatabase db = favoriteMoviesDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        int favMoviesDeleted;

        switch (match) {
            case FAVMOVIES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                favMoviesDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (favMoviesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return favMoviesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
        @Nullable String[] strings) {
        return 0;
    }
}
