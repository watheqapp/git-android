package com.watheq.watheq.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cost")
    @Expose
    private int cost;
    @SerializedName("hasSubs")
    @Expose
    private int hasSubs;
    @SerializedName("subs")
    @Expose
    private ArrayList<Sub> subs = null;

    protected Category(Parcel in) {
        name = in.readString();
        cost = in.readInt();
        hasSubs = in.readInt();
        disc = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    @SerializedName("discription")
    @Expose
    private String disc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHasSubs() {
        return hasSubs;
    }

    public void setHasSubs(int hasSubs) {
        this.hasSubs = hasSubs;
    }

    public ArrayList<Sub> getSubs() {
        return subs;
    }

    public void setSubs(ArrayList<Sub> subs) {
        this.subs = subs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(cost);
        parcel.writeInt(hasSubs);
        parcel.writeString(disc);
    }
}