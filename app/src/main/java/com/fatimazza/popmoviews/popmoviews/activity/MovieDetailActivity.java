package com.fatimazza.popmoviews.popmoviews.activity;

import com.fatimazza.popmoviews.popmoviews.BuildConfig;
import com.fatimazza.popmoviews.popmoviews.R;
import com.fatimazza.popmoviews.popmoviews.network.BaseListDao;
import com.fatimazza.popmoviews.popmoviews.network.MovieDetailDao;
import com.fatimazza.popmoviews.popmoviews.network.MovieReviewDao;
import com.fatimazza.popmoviews.popmoviews.network.MovieVideoDao;
import com.fatimazza.popmoviews.popmoviews.network.RetrofitHelper;
import com.fatimazza.popmoviews.popmoviews.utils.Constant;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL = "movie_detail";

    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieVoteAverage;
    private TextView tvMovieSynosis;
    private ImageView ivMoviePoster;

    private RecyclerView rvMovieVideos;

    private long movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = 0;

        initComponent();
        loadDataFromIntent();
        callMovieDetailAPI(movieId);

    }

    private void initComponent() {
        tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        tvMovieVoteAverage = (TextView) findViewById(R.id.tv_movie_vote_average);
        tvMovieSynosis = (TextView) findViewById(R.id.tv_movie_synopsis);
        ivMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        rvMovieVideos = (RecyclerView) findViewById(R.id.rv_movie_videos);
    }

    private void loadDataFromIntent() {
        Intent intent = getIntent();
        if (null != intent && intent.hasExtra(EXTRA_DETAIL)) {
            MovieDetailDao mMovieDetail = intent.getParcelableExtra(EXTRA_DETAIL);
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

            movieId = mMovieDetail.getId();
        }
    }

    private void callMovieDetailAPI(long movieId) {
        //showLoading
        loadReview(movieId);
        loadVideos(movieId);
    }

    private void loadReview(long movieId) {
        RetrofitHelper.getInstance().getMoviesServices()
            .loadMovieReview(movieId, BuildConfig.API_KEY)
            .enqueue(new Callback<BaseListDao<MovieReviewDao>>() {
                @Override
                public void onResponse(Call<BaseListDao<MovieReviewDao>> call,
                    Response<BaseListDao<MovieReviewDao>> response) {
                    if (response.body() != null) {
                    } else {
                        //show Error Messages
                    }
                    //hide loading Indicator
                }

                @Override
                public void onFailure(Call<BaseListDao<MovieReviewDao>> call, Throwable t) {
                    Log.d("retroFailure ", t.getMessage());
                    //show Error Messages
                    //hide loading Indicator
                }
            });
    }

    private void loadVideos(long movieId) {
        RetrofitHelper.getInstance().getMoviesServices()
            .loadMovieVideos(movieId, BuildConfig.API_KEY)
            .enqueue(new Callback<BaseListDao<MovieVideoDao>>() {
                @Override
                public void onResponse(Call<BaseListDao<MovieVideoDao>> call,
                    Response<BaseListDao<MovieVideoDao>> response) {
                    if (response.body() != null) {
                    } else {
                        //show Error Messages
                    }
                    //hide loading Indicator
                }

                @Override
                public void onFailure(Call<BaseListDao<MovieVideoDao>> call, Throwable t) {
                    Log.d("retroFailure ", t.getMessage());
                    //show Error Messages
                    //hide loading Indicator
                }
            });
    }
}
