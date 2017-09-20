package com.fatimazza.popmoviews.popmoviews.activity;

import com.fatimazza.popmoviews.popmoviews.R;
import com.fatimazza.popmoviews.popmoviews.network.MovieDetailDao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL = "movie_detail";

    private MovieDetailDao mMovieDetail;

    TextView tvMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);

        Intent intent = getIntent();
        if (intent!= null) {
            if (intent.hasExtra(EXTRA_DETAIL)){
                mMovieDetail = intent.getParcelableExtra(EXTRA_DETAIL);
                tvMovieTitle.setText(mMovieDetail.getTitle());
            }
        }
    }
}
