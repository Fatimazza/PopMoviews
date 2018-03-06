package com.fatimazza.popmoviews.popmoviews.network;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BaseListDao<T> implements Parcelable {

    private int page;
    private int total_results;
    private int total_pages;
    private List<T> results;

    public BaseListDao() {

    }

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<T> getResults() {
        return results;
    }

    protected BaseListDao(Parcel in) {
        page = in.readInt();
        total_results = in.readInt();
        total_pages = in.readInt();
    }

    public static final Creator<BaseListDao> CREATOR = new Creator<BaseListDao>() {
        @Override
        public BaseListDao createFromParcel(Parcel in) {
            return new BaseListDao(in);
        }

        @Override
        public BaseListDao[] newArray(int size) {
            return new BaseListDao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(page);
        parcel.writeInt(total_results);
        parcel.writeInt(total_pages);
    }
}
