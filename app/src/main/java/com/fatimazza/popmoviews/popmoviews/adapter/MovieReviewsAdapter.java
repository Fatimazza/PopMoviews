package com.fatimazza.popmoviews.popmoviews.adapter;

import com.fatimazza.popmoviews.popmoviews.R;
import com.fatimazza.popmoviews.popmoviews.network.MovieReviewDao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsAdapterViewHolder> {

    private List<MovieReviewDao> mMovieReviews = new ArrayList<>();

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
        MovieReviewDao movieReviews = mMovieReviews.get(position);
        holder.mVideoReview.setText(movieReviews.getContent());
        holder.mVideoReviewAuthor.setText(movieReviews.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (null == mMovieReviews) return 0;
        return mMovieReviews.size();
    }

    class MovieReviewsAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView mVideoReview;
        private final TextView mVideoReviewAuthor;

        public MovieReviewsAdapterViewHolder(View itemView) {
            super(itemView);
            mVideoReview = (TextView) itemView.findViewById(R.id.tv_movie_review);
            mVideoReviewAuthor = (TextView) itemView.findViewById(R.id.tv_movie_review_author);
        }
    }

    public MovieReviewsAdapter(List<MovieReviewDao> mMovieReviews) {
        this.mMovieReviews = mMovieReviews;
    }
}
