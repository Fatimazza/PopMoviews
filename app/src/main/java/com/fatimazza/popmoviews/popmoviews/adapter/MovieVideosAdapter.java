package com.fatimazza.popmoviews.popmoviews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosAdapterViewHolder> {

    private String [] mMovieVideos = { "Video1", "Video2", "Video3" };

    @Override
    public MovieVideosAdapterViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieVideosAdapterViewHolder holder,
        int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MovieVideosAdapterViewHolder extends RecyclerView.ViewHolder {

        public MovieVideosAdapterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
