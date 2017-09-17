package com.fatimazza.popmoviews.popmoviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder>{

    private String [] mMoviesData = { "Movie1", "Movie2", "Movie3" };

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {

        Context context = parent.getContext();
        int layoutIdForListMovieItem = R.layout.movie_list_item;
        boolean shouldAttachToParentImmediately = false;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListMovieItem, parent, shouldAttachToParentImmediately);

        MoviesAdapterViewHolder viewHolder = new MoviesAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
        String movieData = mMoviesData[position];
        holder.mMovieTitle.setText(movieData);
    }

    @Override
    public int getItemCount() {
        if (null == mMoviesData) return 0;
        return mMoviesData.length;
    }

    void setmMoviesData (String[] moviesData) {
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }

    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView mMovieTitle;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
        }
    }

}
