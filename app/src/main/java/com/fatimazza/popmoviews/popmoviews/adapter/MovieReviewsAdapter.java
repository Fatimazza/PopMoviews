package com.fatimazza.popmoviews.popmoviews.adapter;

import com.fatimazza.popmoviews.popmoviews.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsAdapterViewHolder> {

    private String [] mMovieReviews = { "Review1", "Review2", "Review3" };

    @Override
    public MovieReviewsAdapterViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {

        Context context = parent.getContext();
        int layoutIdForListMovieReviewsItem = R.layout.movie_reviews_list_item;
        boolean shouldAttachToParentImmediately = true;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListMovieReviewsItem, parent, shouldAttachToParentImmediately);

        MovieReviewsAdapterViewHolder viewHolder = new MovieReviewsAdapterViewHolder(view);
        return viewHolder;
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
