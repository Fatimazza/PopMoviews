package com.fatimazza.popmoviews.popmoviews.adapter;

import com.fatimazza.popmoviews.popmoviews.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosAdapterViewHolder> {

    private String [] mMovieVideos = { "Video1", "Video2", "Video3" };

    @Override
    public MovieVideosAdapterViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {

        Context context = parent.getContext();
        int layoutIdForListMovieVideosItem = R.layout.movie_videos_list_item;
        boolean shouldAttachToParentImmediately = false;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListMovieVideosItem, parent, shouldAttachToParentImmediately);

        MovieVideosAdapterViewHolder viewHolder = new MovieVideosAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieVideosAdapterViewHolder holder,
        int position) {

    }

    @Override
    public int getItemCount() {
        if (null == mMovieVideos) return 0;
        return mMovieVideos.length;
    }

    class MovieVideosAdapterViewHolder extends RecyclerView.ViewHolder {

        public MovieVideosAdapterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
