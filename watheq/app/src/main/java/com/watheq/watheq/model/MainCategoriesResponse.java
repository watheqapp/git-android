package com.watheq.watheq.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainCategoriesResponse extends BaseModel {
    private Throwable throwable;

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
