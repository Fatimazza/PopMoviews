package com.fatimazza.popmoviews.popmoviews.activity;


import com.fatimazza.popmoviews.popmoviews.BuildConfig;
import com.fatimazza.popmoviews.popmoviews.adapter.MoviesAdapter;
import com.fatimazza.popmoviews.popmoviews.R;
import com.fatimazza.popmoviews.popmoviews.data.FavoriteMoviesContract;
import com.fatimazza.popmoviews.popmoviews.data.FavoriteMoviesDbManager;
import com.fatimazza.popmoviews.popmoviews.network.BaseListDao;
import com.fatimazza.popmoviews.popmoviews.network.MovieDetailDao;
import com.fatimazza.popmoviews.popmoviews.network.RetrofitHelper;
import com.fatimazza.popmoviews.popmoviews.utils.Constant;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapter.GridItemsClickListener {

    private RecyclerView mMoviesRecyclerView;
    private MoviesAdapter mMoviesAdapter;

    private ProgressBar mLoadingIndicator;
    private TextView mTextErrorMessage;

    private List<MovieDetailDao> mDataMovies = new ArrayList<>();

    private String mMovieType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        mMovieType = getResources().getString(R.string.item_movie_popular);

        initComponent();
        loadMoviesData();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mMovieType.equals(getResources().getString(R.string.item_movie_favorites))) {
            loadFavoriteMoviesFromDatabase();
        }
    }

    private void initComponent() {
        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.rvMovies);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mTextErrorMessage = (TextView) findViewById(R.id.tv_error_message_display);

        int numOfGridColumns = 3;
        GridLayoutManager layoutManager =
            new GridLayoutManager(this, numOfGridColumns, GridLayoutManager.VERTICAL, false);

        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this, mDataMovies);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);
    }

    private void loadMoviesData() {
        if (mMovieType.equals(getResources().getString(R.string.item_movie_favorites))) {
            callAPI(mMovieType);
        } else {
            if (isOnline()) {
                callAPI(mMovieType);
            } else {
                showErrorMessage();
            }
        }
    }

    private void callAPI(String movieType) {
        showLoading();
        if (movieType.equals(getResources().getString(R.string.item_movie_popular))){
            loadPopularMovies();
        } else if (movieType.equals(getResources().getString(R.string.item_movie_top_rated))) {
            loadTopRatedMovies();
        } else if (movieType.equals(getResources().getString(R.string.item_movie_favorites))) {
            loadFavoriteMoviesFromDatabase();
        }
    }

    private void loadPopularMovies() {
        RetrofitHelper.getInstance().getMoviesServices()
            .fetchPopularMovies(BuildConfig.API_KEY)
            .enqueue(new Callback<BaseListDao<MovieDetailDao>>() {
                @Override
                public void onResponse(Call<BaseListDao<MovieDetailDao>> call,
                    Response<BaseListDao<MovieDetailDao>> response) {
                    if (response.body() != null) {
                        mDataMovies.clear();
                        mDataMovies.addAll(response.body().getResults());
                        mMoviesAdapter.notifyDataSetChanged();
                        showMoviesGridView();
                    } else {
                        showErrorMessage();
                    }
                    mLoadingIndicator.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<BaseListDao<MovieDetailDao>> call, Throwable t) {
                    Log.d("retroFailure ", t.getMessage());
                    showErrorMessage();
                    mLoadingIndicator.setVisibility(View.GONE);
                }
            });
    }

    private void loadTopRatedMovies() {
        RetrofitHelper.getInstance().getMoviesServices()
            .fetchTopRatedMovies(BuildConfig.API_KEY)
            .enqueue(new Callback<BaseListDao<MovieDetailDao>>() {
                @Override
                public void onResponse(Call<BaseListDao<MovieDetailDao>> call,
                    Response<BaseListDao<MovieDetailDao>> response) {
                    if (response.body() != null) {
                        mDataMovies.clear();
                        mDataMovies.addAll(response.body().getResults());
                        mMoviesAdapter.notifyDataSetChanged();
                        showMoviesGridView();
                    } else {
                        showErrorMessage();
                    }
                    mLoadingIndicator.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<BaseListDao<MovieDetailDao>> call, Throwable t) {
                    Log.d("retroFailure ", t.getMessage());
                    showErrorMessage();
                    mLoadingIndicator.setVisibility(View.GONE);
                }
            });
    }

    private void loadFavoriteMoviesFromDatabase() {
        Uri mUri = FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI;
        List<MovieDetailDao> mMovieData = FavoriteMoviesDbManager
            .readFavoriteMovie(getContentResolver().query(
                mUri, null, null, null, null));
        mDataMovies.clear();
        mDataMovies.addAll(mMovieData);
        mMoviesAdapter.notifyDataSetChanged();
        showMoviesGridView();
        mLoadingIndicator.setVisibility(View.GONE);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mMoviesRecyclerView.setVisibility(View.GONE);
    }

    private void showMoviesGridView() {
        mTextErrorMessage.setVisibility(View.GONE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mTextErrorMessage.setVisibility(View.VISIBLE);
        mMoviesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onGridItemsClickListener(Context context, MovieDetailDao movieDetail) {
        Boolean isFavorited = FavoriteMoviesDbManager.isFavorited(
            this, String.valueOf(movieDetail.getId()));
        Class classDestination = MovieDetailActivity.class;
        Intent intent = new Intent(context, classDestination);
        intent.putExtra(Constant.EXTRA_DETAIL, movieDetail);
        intent.putExtra(Constant.EXTRA_IS_FAVORITED, isFavorited);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sortby_popular:
                mMovieType = getResources().getString(R.string.item_movie_popular);
                loadMoviesData();
                return true;
            case R.id.action_sortby_toprated:
                mMovieType = getResources().getString(R.string.item_movie_top_rated);
                loadMoviesData();
                return true;
            case R.id.action_sortby_favorites:
                mMovieType = getResources().getString(R.string.item_movie_favorites);
                loadMoviesData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
