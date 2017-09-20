package com.fatimazza.popmoviews.popmoviews.network;


import java.util.List;

public class MovieDao {

    private int page;
    private int total_results;
    private int total_pages;
    private List<MovieDetailDao> results;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<MovieDetailDao> getResults() {
        return results;
    }
}
