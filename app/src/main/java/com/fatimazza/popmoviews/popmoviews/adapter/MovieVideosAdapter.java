package com.fatimazza.popmoviews.popmoviews.adapter;

import com.fatimazza.popmoviews.popmoviews.R;
import com.fatimazza.popmoviews.popmoviews.network.MovieVideoDao;
import com.fatimazza.popmoviews.popmoviews.utils.Constant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosAdapterViewHolder> {

    private List<MovieVideoDao> mMovieVideos = new ArrayList<>();

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
        MovieVideoDao movieVideos = mMovieVideos.get(position);
        holder.mVideoTitle.setText(movieVideos.getName());

        String imagePath = Constant.MOVIES_VIDEO_BASE_URL
            +movieVideos.getKey() +Constant.MOVIES_VIDEO_EXTENSION;
    }

    @Override
    public int getItemCount() {
        if (null == mMovieVideos) return 0;
        return mMovieVideos.size();
    }

    class MovieVideosAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView mVideoTitle;
        private final ImageView mVideoThumbnail;

        public MovieVideosAdapterViewHolder(View itemView) {
            super(itemView);
            mVideoTitle = itemView.findViewById(R.id.tv_video_title);
            mVideoThumbnail = itemView.findViewById(R.id.iv_video_thumbnail);
        }
    }

    public MovieVideosAdapter(List<MovieVideoDao> mMovieVideos) {
        this.mMovieVideos = mMovieVideos;
    }
}
