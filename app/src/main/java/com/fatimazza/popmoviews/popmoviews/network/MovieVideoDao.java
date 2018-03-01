package com.fatimazza.popmoviews.popmoviews.network;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieVideoDao implements Parcelable {

    private String id;
    private String key;
    private String name;
    private long size;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }


    protected MovieVideoDao(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        size = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeLong(size);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieVideoDao> CREATOR = new Creator<MovieVideoDao>() {
        @Override
        public MovieVideoDao createFromParcel(Parcel in) {
            return new MovieVideoDao(in);
        }

        @Override
        public MovieVideoDao[] newArray(int size) {
            return new MovieVideoDao[size];
        }
    };
}
