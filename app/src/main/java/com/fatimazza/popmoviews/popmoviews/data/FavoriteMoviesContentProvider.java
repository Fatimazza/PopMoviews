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
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = favoriteMoviesDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor;

        switch (match) {
            case FAVMOVIES:
                returnCursor = db.query(TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        final SQLiteDatabase db = favoriteMoviesDbHelper.getWritableDatabase();
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
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final  SQLiteDatabase db = favoriteMoviesDbHelper.getWritableDatabase();
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
    public int update(@NonNull Uri uri, ContentValues values, String selection,
        String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
