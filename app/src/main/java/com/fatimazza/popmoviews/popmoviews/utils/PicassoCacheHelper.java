package com.fatimazza.popmoviews.popmoviews.utils;


import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import android.content.Context;

public class PicassoCacheHelper {

    private static Picasso picasso = null;

    public PicassoCacheHelper(Context context) {
        Downloader downloader = new OkHttp3Downloader(context);

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(downloader);

        picasso = builder.build();
    }

    public static Picasso getInstance(Context context) {
        if (picasso == null) {
            new PicassoCacheHelper(context);
            return picasso;
        }
        return picasso;
    }
}
