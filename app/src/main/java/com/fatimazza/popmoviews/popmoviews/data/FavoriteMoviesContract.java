package com.fatimazza.popmoviews.popmoviews.data;


import android.provider.BaseColumns;

public class FavoriteMoviesContract {

    public static final class FavoriteMoviesEntry implements BaseColumns {

        public static final String TABLE_NAME = "favmovies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_VOTE = "movie_vote_average";
        public static final String COLUMN_MOVIE_POSTER = "movie_poster_path";
        public static final String COLUMN_MOVIE_RELEASE = "movie_release_date";
        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";

    }

}
