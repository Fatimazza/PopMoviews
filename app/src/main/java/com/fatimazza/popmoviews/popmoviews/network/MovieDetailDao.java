package com.fatimazza.popmoviews.popmoviews.network;


import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetailDao implements Parcelable {

    private long id;
    private double vote_average;
    private String title;
    private String poster_path;
    private String release_date;
    private String overview;

    public long getId() {
        return id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public MovieDetailDao() {

    }

    private MovieDetailDao(Parcel in) {
        id = in.readLong();
        vote_average = in.readDouble();
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        overview = in.readString();
    }

    public static final Creator<MovieDetailDao> CREATOR = new Creator<MovieDetailDao>() {
        @Override
        public MovieDetailDao createFromParcel(Parcel in) {
            return new MovieDetailDao(in);
        }

        @Override
        public MovieDetailDao[] newArray(int size) {
            return new MovieDetailDao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeDouble(vote_average);
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeString(overview);
    }
}
