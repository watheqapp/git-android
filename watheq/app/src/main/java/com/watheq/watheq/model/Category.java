package com.watheq.watheq.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("hasSubs")
    @Expose
    private String hasSubs;
    @SerializedName("subs")
    @Expose
    private ArrayList<Sub> subs = null;

    private String deliveryToHomeFees;

    public String getDeliveryToHomeFees() {
        return deliveryToHomeFees;
    }

    public void setDeliveryToHomeFees(String deliveryToHomeFees) {
        this.deliveryToHomeFees = deliveryToHomeFees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected Category(Parcel in) {
        id = in.readString();
        name = in.readString();
        cost = in.readString();
        hasSubs = in.readString();
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getHasSubs() {
        return hasSubs;
    }

    public void setHasSubs(String hasSubs) {
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
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(cost);
        parcel.writeString(hasSubs);
        parcel.writeString(disc);
    }
}