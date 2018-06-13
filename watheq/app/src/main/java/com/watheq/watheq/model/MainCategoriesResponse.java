package com.watheq.watheq.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainCategoriesResponse extends BaseModel implements Parcelable{
    private Throwable throwable;

    protected MainCategoriesResponse(Parcel in) {
    }

    public static final Creator<MainCategoriesResponse> CREATOR = new Creator<MainCategoriesResponse>() {
        @Override
        public MainCategoriesResponse createFromParcel(Parcel in) {
            return new MainCategoriesResponse(in);
        }

        @Override
        public MainCategoriesResponse[] newArray(int size) {
            return new MainCategoriesResponse[size];
        }
    };

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public MainCategoriesResponse(Throwable throwable) {
        this.throwable = throwable;
    }

    @SerializedName("data")
    @Expose
    private DataResponse data;

    @NonNull
    public DataResponse getData() {
        return data;
    }

    public void setData(DataResponse data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public class DataResponse {

        @SerializedName("deliverToHomeFees")
        @Expose
        private int deliverToHomeFees;
        @SerializedName("categories")
        @Expose
        private ArrayList<Category> categories = null;

        public int getDeliverToHomeFees() {
            return deliverToHomeFees;
        }

        public void setDeliverToHomeFees(int deliverToHomeFees) {
            this.deliverToHomeFees = deliverToHomeFees;
        }

        public ArrayList<Category> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<Category> categories) {
            this.categories = categories;
        }

    }
}
