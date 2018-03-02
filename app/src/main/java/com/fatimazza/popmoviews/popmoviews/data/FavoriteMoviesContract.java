package com.fatimazza.popmoviews.popmoviews.data;


import android.provider.BaseColumns;

public class FavoriteMoviesContract {

    public static final class FavoriteMoviesEntry implements BaseColumns {

        private static final String TABLE_NAME = "favmovies";

        private static final String COLUMN_MOVIE_ID = "movie_id";
        private static final String COLUMN_MOVIE_TITLE = "movie_title";
        private static final String COLUMN_MOVIE_VOTE = "movie_vote_average";
        private static final String COLUMN_MOVIE_POSTER = "movie_poster_path";
        private static final String COLUMN_MOVIE_RELEASE = "movie_release_date";
        private static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";

    }

}
