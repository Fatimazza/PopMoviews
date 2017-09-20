package com.fatimazza.popmoviews.popmoviews.adapter;

import com.fatimazza.popmoviews.popmoviews.R;
import com.fatimazza.popmoviews.popmoviews.network.MovieDetailDao;
import com.fatimazza.popmoviews.popmoviews.utils.Constant;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder>{

    private List<MovieDetailDao> mDataMovies = new ArrayList<>();

    final private GridItemsClickListener mOnClickListener;

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

        final MovieDetailDao mDataItemMovie = mDataMovies.get(position);
        final Context context = holder.mViewRoot.getContext();

        holder.mMovieTitle.setText(mDataItemMovie.getTitle());
        holder.mMovieVoteAverage.setText(String.valueOf(mDataItemMovie.getVote_average()));

        String imagePath = Constant.MOVIES_POSTER_BASE_URL + mDataItemMovie.getPoster_path();

        Picasso.with(context)
            .load(imagePath)
            .into(holder.mMovieThumbnail);
    }

    @Override
    public int getItemCount() {
        if (null == mDataMovies) return 0;
        return mDataMovies.size();
    }

    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mMovieTitle;
        private final ImageView mMovieThumbnail;
        private final TextView mMovieVoteAverage;
        private final View mViewRoot;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            mViewRoot = itemView;
            mMovieTitle = itemView.findViewById(R.id.tv_grid_movies_title);
            mMovieThumbnail = itemView.findViewById(R.id.iv_grid_movies_thumbnail);
            mMovieVoteAverage = itemView.findViewById(R.id.tv_grid_movies_vote_average);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MovieDetailDao movieDetail = mDataMovies.get(getAdapterPosition());
            mOnClickListener.onGridItemsClickListener(view.getContext(), movieDetail);
        }
    }

    public interface GridItemsClickListener {
        void onGridItemsClickListener (Context context, MovieDetailDao movieDetailDao);
    }

    public MoviesAdapter(GridItemsClickListener mOnClickListener, List<MovieDetailDao> mDataMovies) {
        this.mOnClickListener = mOnClickListener;
        this.mDataMovies = mDataMovies;
    }

}
