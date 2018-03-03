package com.fatimazza.popmoviews.popmoviews.data;


import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteMoviesContract {

    public static final String AUTHORITY = "com.fatimazza.popmoviews.popmoviews";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +AUTHORITY);
    public static final String PATH_FAV_MOVIES = "favmovies";

    public static final class FavoriteMoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV_MOVIES).build();

        public static final String TABLE_NAME = "favmovies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_VOTE = "movie_vote_average";
        public static final String COLUMN_MOVIE_POSTER = "movie_poster_path";
        public static final String COLUMN_MOVIE_RELEASE = "movie_release_date";
        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";

    }

}
