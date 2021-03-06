package com.fatimazza.popmoviews.popmoviews.activity;

import com.fatimazza.popmoviews.popmoviews.BuildConfig;
import com.fatimazza.popmoviews.popmoviews.R;
import com.fatimazza.popmoviews.popmoviews.adapter.MovieReviewsAdapter;
import com.fatimazza.popmoviews.popmoviews.adapter.MovieVideosAdapter;
import com.fatimazza.popmoviews.popmoviews.data.FavoriteMoviesDbManager;
import com.fatimazza.popmoviews.popmoviews.network.BaseListDao;
import com.fatimazza.popmoviews.popmoviews.network.MovieDetailDao;
import com.fatimazza.popmoviews.popmoviews.network.MovieReviewDao;
import com.fatimazza.popmoviews.popmoviews.network.MovieVideoDao;
import com.fatimazza.popmoviews.popmoviews.network.RetrofitHelper;
import com.fatimazza.popmoviews.popmoviews.utils.Constant;
import com.fatimazza.popmoviews.popmoviews.utils.PicassoCacheHelper;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity implements MovieVideosAdapter.ItemsClickListener{

    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieVoteAverage;
    private TextView tvMovieSynosis;
    private ImageView ivMoviePoster;

    private ImageView ivFavoriteMovie;

    private RecyclerView rvMovieVideos;
    private MovieVideosAdapter mMovieVideosAdapter;
    private LinearLayoutManager mMovieVideosLayoutManager;
    private List<MovieVideoDao> mDataVideo = new ArrayList<>();

    private RecyclerView rvMovieReviews;
    private MovieReviewsAdapter mMovieReviewsAdapter;
    private LinearLayoutManager mMovieReviewsLayoutManager;
    private List<MovieReviewDao> mDataReview = new ArrayList<>();

    private long movieId;
    private boolean isFavorited;

    private MovieDetailDao mMovieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ActionBar actionBar= this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        movieId = 0;

        initComponent();
        initAdapters();
        loadDataFromIntent();
        callMovieDetailAPI(movieId);

    }

    private void initComponent() {
        tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        tvMovieVoteAverage = (TextView) findViewById(R.id.tv_movie_vote_average);
        tvMovieSynosis = (TextView) findViewById(R.id.tv_movie_synopsis);
        ivMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        ivFavoriteMovie = (ImageView) findViewById(R.id.iv_favorite);
        rvMovieVideos = (RecyclerView) findViewById(R.id.rv_movie_videos);
        rvMovieReviews = (RecyclerView) findViewById(R.id.rv_movie_reviews);
    }

    private void initAdapters() {
        initVideosAdapter();
        initReviewsAdapter();
    }

    private void initVideosAdapter() {
        mMovieVideosLayoutManager = new LinearLayoutManager(this);

        rvMovieVideos.setLayoutManager(mMovieVideosLayoutManager);
        rvMovieVideos.setHasFixedSize(true);

        mMovieVideosAdapter = new MovieVideosAdapter(this, mDataVideo);
        rvMovieVideos.setAdapter(mMovieVideosAdapter);
    }

    private void initReviewsAdapter() {
        mMovieReviewsLayoutManager = new LinearLayoutManager(this);

        rvMovieReviews.setLayoutManager(mMovieReviewsLayoutManager);
        rvMovieReviews.setHasFixedSize(true);

        mMovieReviewsAdapter = new MovieReviewsAdapter(mDataReview);
        rvMovieReviews.setAdapter(mMovieReviewsAdapter);
    }

    private void loadDataFromIntent() {
        Intent intent = getIntent();
        if (null != intent
            && intent.hasExtra(Constant.EXTRA_DETAIL)
            && intent.hasExtra(Constant.EXTRA_IS_FAVORITED)) {

            mMovieDetail = intent.getParcelableExtra(Constant.EXTRA_DETAIL);
            tvMovieTitle.setText(mMovieDetail.getTitle());
            tvMovieReleaseDate.setText(mMovieDetail.getRelease_date());
            tvMovieVoteAverage.setText(String.valueOf(mMovieDetail.getVote_average()));
            tvMovieSynosis.setText(mMovieDetail.getOverview());

            isFavorited = intent.getBooleanExtra(Constant.EXTRA_IS_FAVORITED, false);

            markAsFavorite(isFavorited);

            String imagePath = Constant.MOVIES_POSTER_BASE_URL + mMovieDetail.getPoster_path();

            PicassoCacheHelper
                .getInstance(this)
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
                        mDataReview.clear();
                        mDataReview.addAll(response.body().getResults());
                        mMovieReviewsAdapter.notifyDataSetChanged();
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
                        mDataVideo.clear();
                        mDataVideo.addAll(response.body().getResults());
                        mMovieVideosAdapter.notifyDataSetChanged();
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

    public void onFavoriteClick(View view) {
        isFavorited = !isFavorited;
        markAsFavorite(isFavorited);

    }

    private void markAsFavorite(boolean isFavorited) {
        int imageResourceId;
        if (isFavorited) {
            imageResourceId = R.drawable.ic_favorite_red_500_24dp;
            if (!FavoriteMoviesDbManager.isFavorited(this, String.valueOf(mMovieDetail.getId()))) {
                FavoriteMoviesDbManager.insertFavoriteMovie(this, mMovieDetail);
            }
        } else {
            imageResourceId = R.drawable.ic_favorite_white_24dp;
            FavoriteMoviesDbManager.deleteFavoriteMovie(this, String.valueOf(mMovieDetail.getId()));
        }
        ivFavoriteMovie.setImageDrawable(ContextCompat.getDrawable(this, imageResourceId));
    }

    @Override
    public void onItemsClickListener(String movieUrlKey) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getYoutubeUrl(movieUrlKey)));
        startActivity(intent);
    }

    private String getYoutubeUrl(String key) {
        return Constant.YOUTUBE_BASE_URL + key;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
