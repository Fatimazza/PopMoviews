package com.fatimazza.popmoviews.popmoviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MoviesActivity extends AppCompatActivity {

    private RecyclerView mMoviesRecyclerView;
    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.rvMovies);

        int numOfGridColumns = 3;
        GridLayoutManager layoutManager =
            new GridLayoutManager(this, numOfGridColumns, GridLayoutManager.VERTICAL, false);

        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter();
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);
    }
}
