package com.fatimazza.popmoviews.popmoviews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MoviesActivity extends AppCompatActivity implements MoviesAdapter.GridItemsClickListener {

    private RecyclerView mMoviesRecyclerView;
    private MoviesAdapter mMoviesAdapter;

    private TextView mTextErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initComponent();
        displayMoviesData();
    }

    private void initComponent() {
        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.rvMovies);
        mTextErrorMessage = (TextView) findViewById(R.id.tv_error_message_display);

        int numOfGridColumns = 3;
        GridLayoutManager layoutManager =
            new GridLayoutManager(this, numOfGridColumns, GridLayoutManager.VERTICAL, false);

        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);
    }

    private void displayMoviesData() {
        if (!isOnline()){
            mTextErrorMessage.setVisibility(View.VISIBLE);
            mMoviesRecyclerView.setVisibility(View.GONE);
        } else {
            mTextErrorMessage.setVisibility(View.GONE);
            mMoviesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
