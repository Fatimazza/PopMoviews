package com.fatimazza.popmoviews.popmoviews.adapter;

import com.fatimazza.popmoviews.popmoviews.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsAdapterViewHolder> {

    private String [] mMovieReviews = { "Review1", "Review2", "Review3" };

    @Override
    public MovieReviewsAdapterViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {

        Context context = parent.getContext();
        int layoutIdForListMovieReviewsItem = R.layout.movie_reviews_list_item;
        boolean shouldAttachToParentImmediately = false;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListMovieReviewsItem, parent, shouldAttachToParentImmediately);

        MovieReviewsAdapterViewHolder viewHolder = new MovieReviewsAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieReviewsAdapterViewHolder holder,
        int position) {
        String movieReviews = mMovieReviews[position];
        holder.mVideoReview.setText(movieReviews);
        holder.mVideoReviewAuthor.setText(movieReviews);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieReviews) return 0;
        return mMovieReviews.length;
    }

    class MovieReviewsAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView mVideoReview;
        private final TextView mVideoReviewAuthor;

        public MovieReviewsAdapterViewHolder(View itemView) {
            super(itemView);
            mVideoReview = itemView.findViewById(R.id.tv_movie_review);
            mVideoReviewAuthor = itemView.findViewById(R.id.tv_movie_review_author);
        }
    }

}
