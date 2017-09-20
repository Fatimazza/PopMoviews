package com.fatimazza.popmoviews.popmoviews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import java.io.IOException;
import java.net.URL;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapter.GridItemsClickListener {

    private static final String TAG = MoviesActivity.class.getSimpleName();

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
            String movieId = "211672";
            new FetchMoviesDataTask().execute(movieId);
        }
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

    public class FetchMoviesDataTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String movieId = params[0];
            URL moviesDataRequestURL = NetworkUtils.buildURL(movieId);

            try {
                String jsonMovieDetailResponse =
                    NetworkUtils.getResponseFromHttpUrl(moviesDataRequestURL);

                Log.d(TAG, "JsonMovieResponse " + jsonMovieDetailResponse);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] moviesData) {
            mLoadingIndicator.setVisibility(View.GONE);
            if (moviesData != null) {
                showMoviesGridView();
                mMoviesAdapter.setmMoviesData(moviesData);
            } else {
                showErrorMessage();
            }
        }
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
