package com.fatimazza.popmoviews.popmoviews.network;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReviewDao implements Parcelable {

    String id;
    String author;
    String content;
    String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    protected MovieReviewDao(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<MovieReviewDao> CREATOR = new Creator<MovieReviewDao>() {
        @Override
        public MovieReviewDao createFromParcel(Parcel in) {
            return new MovieReviewDao(in);
        }

        @Override
        public MovieReviewDao[] newArray(int size) {
            return new MovieReviewDao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }
}
