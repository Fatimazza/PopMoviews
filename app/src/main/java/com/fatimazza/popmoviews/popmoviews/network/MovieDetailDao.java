package com.fatimazza.popmoviews.popmoviews.network;


public class MovieDetailDao {

    private int id;
    private double vote_average;
    private String title;
    private String poster_path;
    private String release_date;
    private String overview;

    public int getId() {
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
}
