package com.watheq.watheq.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Sub implements Parcelable{

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

    @SerializedName("discription")
    @Expose
    private String disc;

    protected Sub(Parcel in) {
        name = in.readString();
        cost = in.readInt();
        hasSubs = in.readInt();
        subs = in.createTypedArrayList(Sub.CREATOR);
        disc = in.readString();
    }

    public static final Creator<Sub> CREATOR = new Creator<Sub>() {
        @Override
        public Sub createFromParcel(Parcel in) {
            return new Sub(in);
        }

        @Override
        public Sub[] newArray(int size) {
            return new Sub[size];
        }
    };

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

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
        parcel.writeTypedList(subs);
        parcel.writeString(disc);
    }
}