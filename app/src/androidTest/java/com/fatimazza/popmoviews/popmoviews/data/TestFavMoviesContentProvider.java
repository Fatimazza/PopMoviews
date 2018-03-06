/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.fatimazza.popmoviews.popmoviews.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class TestFavMoviesContentProvider {

    /* Context used to access various parts of the system */
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    /**
     * Because we annotate this method with the @Before annotation, this method will be called
     * before every single method with an @Test annotation. We want to start each test clean, so we
     * delete all entries in the favMovies directory to do so.
     */
    @Before
    public void setUp() {
        /* Use FavoriteMoviesDbHelper to get access to a writable database */
        FavoriteMoviesDbHelper dbHelper = new FavoriteMoviesDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_NAME, null, null);
    }


    //================================================================================
    // Test ContentProvider Registration
    //================================================================================


    /**
     * This test checks to make sure that the content provider is registered correctly in the
     * AndroidManifest file. If it fails, you should check the AndroidManifest to see if you've
     * added a <provider/> tag and that you've properly specified the android:authorities attribute.
     */
    @Test
    public void testProviderRegistry() {

        /*
         * A ComponentName is an identifier for a specific application component, such as an
         * Activity, ContentProvider, BroadcastReceiver, or a Service.
         *
         * Two pieces of information are required to identify a component: the package (a String)
         * it exists in, and the class (a String) name inside of that package.
         *
         * We will use the ComponentName for our ContentProvider class to ask the system
         * information about the ContentProvider, specifically, the authority under which it is
         * registered.
         */
        String packageName = mContext.getPackageName();
        String favMoviesProviderClassName = FavoriteMoviesContentProvider.class.getName();
        ComponentName componentName = new ComponentName(packageName, favMoviesProviderClassName);

        try {

            /*
             * Get a reference to the package manager. The package manager allows us to access
             * information about packages installed on a particular device. In this case, we're
             * going to use it to get some information about our ContentProvider under test.
             */
            PackageManager pm = mContext.getPackageManager();

            /* The ProviderInfo will contain the authority, which is what we want to test */
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority = providerInfo.authority;
            String expectedAuthority = packageName;

            /* Make sure that the registered authority matches the authority from the Contract */
            String incorrectAuthority =
                    "Error: FavoriteMoviesContentProvider registered with authority: " + actualAuthority +
                            " instead of expected authority: " + expectedAuthority;
            assertEquals(incorrectAuthority,
                    actualAuthority,
                    expectedAuthority);

        } catch (PackageManager.NameNotFoundException e) {
            String providerNotRegisteredAtAll =
                    "Error: FavoriteMoviesContentProvider not registered at " + mContext.getPackageName();
            /*
             * This exception is thrown if the ContentProvider hasn't been registered with the
             * manifest at all. If this is the case, you need to double check your
             * AndroidManifest file
             */
            fail(providerNotRegisteredAtAll);
        }
    }


    //================================================================================
    // Test UriMatcher
    //================================================================================


    private static final Uri TEST_FAV_MOVIES = FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI;
    // Content URI for a single favorite movies with id = 1
    private static final Uri TEST_FAV_MOVIES_WITH_ID = TEST_FAV_MOVIES.buildUpon().appendPath("1").build();


    /**
     * This function tests that the UriMatcher returns the correct integer value for
     * each of the Uri types that the ContentProvider can handle. Uncomment this when you are
     * ready to test your UriMatcher.
     */
    @Test
    public void testUriMatcher() {

        /* Create a URI matcher that the FavoriteMoviesContentProvider uses */
        UriMatcher testMatcher = FavoriteMoviesContentProvider.buildUriMatcher();

        /* Test that the code returned from our matcher matches the expected FAV_MOVIES int */
        String favMoviesUriDoesNotMatch = "Error: The FAV_MOVIES was matched incorrectly.";
        int actualFavMoviesMatchCode = testMatcher.match(TEST_FAV_MOVIES);
        int expectedFavMoviesMatchCode = FavoriteMoviesContentProvider.FAVMOVIES;
        assertEquals(favMoviesUriDoesNotMatch,
                actualFavMoviesMatchCode,
                expectedFavMoviesMatchCode);

        /* Test that the code returned from our matcher matches the expected FAV_MOVIES_WITH_ID */
        String favMovieWithIdDoesNotMatch =
                "Error: The TEST_FAV_MOVIES URI was matched incorrectly.";
        int actualFavMoviesWithIdCode = testMatcher.match(TEST_FAV_MOVIES_WITH_ID);
        int expectedFavMoviesWithIdCode = FavoriteMoviesContentProvider.FAVMOVIES_WITH_ID;
        assertEquals(favMovieWithIdDoesNotMatch,
                actualFavMoviesWithIdCode,
            expectedFavMoviesWithIdCode);
    }


    //================================================================================
    // Test Insert
    //================================================================================


    /**
     * Tests inserting a single row of data via a ContentResolver
     */
    @Test
    public void testInsert() {

        /* Create values to insert */
        ContentValues testFavMoviesValues = new ContentValues();
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_ID, 1212);
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_TITLE, "title");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_VOTE, "4.5");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_POSTER, "poster.jpg");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_RELEASE, "12-12-12");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_OVERVIEW, "this is overview");

        /* TestContentObserver allows us to test if notifyChange was called appropriately */
        TestUtilities.TestContentObserver favMovieObserver = TestUtilities.getTestContentObserver();

        ContentResolver contentResolver = mContext.getContentResolver();

        /* Register a content observer to be notified of changes to data at a given URI (favMovies) */
        contentResolver.registerContentObserver(
                /* URI that we would like to observe changes to */
                FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI,
                /* Whether or not to notify us if descendants of this URI change */
                true,
                /* The observer to register (that will receive notifyChange callbacks) */
                favMovieObserver);


        Uri uri = contentResolver.insert(FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI, testFavMoviesValues);


        Uri expectedUri = ContentUris.withAppendedId(FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI, 1);

        String insertProviderFailed = "Unable to insert item through Provider";
        assertEquals(insertProviderFailed, uri, expectedUri);

        /*
         * If this fails, it's likely you didn't call notifyChange in your insert method from
         * your ContentProvider.
         */
        favMovieObserver.waitForNotificationOrFail();

        /*
         * waitForNotificationOrFail is synchronous, so after that call, we are done observing
         * changes to content and should therefore unregister this observer.
         */
        contentResolver.unregisterContentObserver(favMovieObserver);
    }


    //================================================================================
    // Test Query (for favMovies directory)
    //================================================================================


    /**
     * Inserts data, then tests if a query for the favMovies directory returns that data as a Cursor
     */
    @Test
    public void testQuery() {

        /* Get access to a writable database */
        FavoriteMoviesDbHelper dbHelper = new FavoriteMoviesDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        /* Create values to insert */
        ContentValues testFavMoviesValues = new ContentValues();
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_ID, 1212);
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_TITLE, "title");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_VOTE, "4.5");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_POSTER, "poster.jpg");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_RELEASE, "12-12-12");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_OVERVIEW, "this is overview");

        /* Insert ContentValues into database and get a row ID back */
        long favMovieRowId = database.insert(
                /* Table to insert values into */
                FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_NAME,
                null,
                /* Values to insert into table */
                testFavMoviesValues);

        String insertFailed = "Unable to insert directly into the database";
        assertTrue(insertFailed, favMovieRowId != -1);

        /* We are done with the database, close it now. */
        database.close();

        /* Perform the ContentProvider query */
        Cursor favMovieCursor = mContext.getContentResolver().query(
            FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Sort order to return in Cursor */
                null);


        String queryFailed = "Query failed to return a valid Cursor";
        assertTrue(queryFailed, favMovieCursor != null);

        /* We are done with the cursor, close it now. */
        favMovieCursor.close();
    }


    //================================================================================
    // Test Delete (for a single item)
    //================================================================================


    /**
     * Tests deleting a single row of data via a ContentResolver
     */
    @Test
    public void testDelete() {
        /* Access writable database */
        FavoriteMoviesDbHelper helper = new FavoriteMoviesDbHelper(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        /* Create a new row of favMovie data */
        ContentValues testFavMoviesValues = new ContentValues();
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_ID, 1212);
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_TITLE, "title");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_VOTE, "4.5");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_POSTER, "poster.jpg");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_RELEASE, "12-12-12");
        testFavMoviesValues.put(FavoriteMoviesContract.FavoriteMoviesEntry.COLUMN_MOVIE_OVERVIEW, "this is overview");

        /* Insert ContentValues into database and get a row ID back */
        long favMovieRowId = database.insert(
                /* Table to insert values into */
                FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_NAME,
                null,
                /* Values to insert into table */
                testFavMoviesValues);

        /* Always close the database when you're through with it */
        database.close();

        String insertFailed = "Unable to insert into the database";
        assertTrue(insertFailed, favMovieRowId != -1);


        /* TestContentObserver allows us to test if notifyChange was called appropriately */
        TestUtilities.TestContentObserver favMovieObserver = TestUtilities.getTestContentObserver();

        ContentResolver contentResolver = mContext.getContentResolver();

        /* Register a content observer to be notified of changes to data at a given URI (favMovies) */
        contentResolver.registerContentObserver(
                /* URI that we would like to observe changes to */
                FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI,
                /* Whether or not to notify us if descendants of this URI change */
                true,
                /* The observer to register (that will receive notifyChange callbacks) */
                favMovieObserver);



        /* The delete method deletes the previously inserted row with id = 1 */
        Uri uriToDelete = FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI.buildUpon().appendPath("1").build();
        int favMoviesDeleted = contentResolver.delete(uriToDelete, null, null);

        String deleteFailed = "Unable to delete item in the database";
        assertTrue(deleteFailed, favMoviesDeleted != 0);

        /*
         * If this fails, it's likely you didn't call notifyChange in your delete method from
         * your ContentProvider.
         */
        favMovieObserver.waitForNotificationOrFail();

        /*
         * waitForNotificationOrFail is synchronous, so after that call, we are done observing
         * changes to content and should therefore unregister this observer.
         */
        contentResolver.unregisterContentObserver(favMovieObserver);
    }

}
