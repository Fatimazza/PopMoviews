package com.fatimazza.popmoviews.popmoviews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsAdapterViewHolder> {

    private String [] mMovieReviews = { "Review1", "Review2", "Review3" };

    @Override
    public MovieReviewsAdapterViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieReviewsAdapterViewHolder holder,
        int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MovieReviewsAdapterViewHolder extends RecyclerView.ViewHolder {

        public MovieReviewsAdapterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
