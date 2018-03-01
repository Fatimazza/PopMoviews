package com.fatimazza.popmoviews.popmoviews.activity;

import com.fatimazza.popmoviews.popmoviews.R;
import com.fatimazza.popmoviews.popmoviews.network.MovieDetailDao;
import com.fatimazza.popmoviews.popmoviews.utils.Constant;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL = "movie_detail";

    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieVoteAverage;
    private TextView tvMovieSynosis;
    private ImageView ivMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initComponent();

        Intent intent = getIntent();
        if (intent!= null) {
            if (intent.hasExtra(EXTRA_DETAIL)){
                MovieDetailDao mMovieDetail = intent.getParcelableExtra(EXTRA_DETAIL);
                long movieId = mMovieDetail.getId();
                tvMovieTitle.setText(mMovieDetail.getTitle());
                tvMovieReleaseDate.setText(mMovieDetail.getRelease_date());
                tvMovieVoteAverage.setText(String.valueOf(mMovieDetail.getVote_average()));
                tvMovieSynosis.setText(mMovieDetail.getOverview());

                String imagePath = Constant.MOVIES_POSTER_BASE_URL + mMovieDetail.getPoster_path();

                Picasso.with(this)
                    .load(imagePath)
                    .placeholder(R.color.grey)
                    .error(R.color.grey)
                    .into(ivMoviePoster);
            }
        }
    }

    private void initComponent() {
        tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        tvMovieVoteAverage = (TextView) findViewById(R.id.tv_movie_vote_average);
        tvMovieSynosis = (TextView) findViewById(R.id.tv_movie_synopsis);
        ivMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
    }
}
