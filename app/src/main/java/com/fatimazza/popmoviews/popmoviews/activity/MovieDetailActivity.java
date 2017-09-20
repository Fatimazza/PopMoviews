package com.fatimazza.popmoviews.popmoviews.activity;

import com.fatimazza.popmoviews.popmoviews.R;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    TextView tvMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity!= null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
                String intentExtraText = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                tvMovieTitle.setText("Movie Title is #" +intentExtraText);
            }
        }
    }
}
