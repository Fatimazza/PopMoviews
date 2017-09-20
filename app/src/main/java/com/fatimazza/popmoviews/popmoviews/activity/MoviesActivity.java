package com.fatimazza.popmoviews.popmoviews.activity;


import com.fatimazza.popmoviews.popmoviews.adapter.MoviesAdapter;
import com.fatimazza.popmoviews.popmoviews.R;
import com.fatimazza.popmoviews.popmoviews.network.MovieDao;
import com.fatimazza.popmoviews.popmoviews.network.RetrofitHelper;
import com.fatimazza.popmoviews.popmoviews.utils.Constant;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapter.GridItemsClickListener {

    private RecyclerView mMoviesRecyclerView;
    private MoviesAdapter mMoviesAdapter;

    private ProgressBar mLoadingIndicator;
    private TextView mTextErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initComponent();
        loadMoviesData();
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

        mMoviesAdapter = new MoviesAdapter(this);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);
    }

    private void loadMoviesData() {
        if (!isOnline()){
            showErrorMessage();
        } else {
            showMoviesGridView();
            callAPI();
        }
    }

    private void callAPI() {

        mLoadingIndicator.setVisibility(View.VISIBLE);
        mMoviesRecyclerView.setVisibility(View.GONE);

        RetrofitHelper.getInstance().getMoviesServices()
            .fetchTopRatedMovies(Constant.API_KEY_PARAM)
            .enqueue(new Callback<MovieDao>() {
                @Override
                public void onResponse(Call<MovieDao> call, Response<MovieDao> response) {
                    if (response.body() != null) {
                        Log.d("retroSuccess ", response.body().getResults().get(0).getOverview());
                        showMoviesGridView();
                    } else {
                        showErrorMessage();
                    }
                    mLoadingIndicator.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<MovieDao> call, Throwable t) {
                    Log.d("retroFailure ", t.getMessage());
                    showErrorMessage();
                    mLoadingIndicator.setVisibility(View.GONE);
                }
            });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
    public void onGridItemsClickListener(Context context, int clickedItemIndex) {
        Class classDestination = MovieDetailActivity.class;
        Intent intent = new Intent(context, classDestination);
        intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(clickedItemIndex));
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
                Toast.makeText(this, "Menu " + getResources().getString(R.string.item_movie_popular), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_sortby_toprated:
                Toast.makeText(this, "Menu " + getResources().getString(R.string.item_movie_top_rated), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
