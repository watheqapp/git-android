package com.watheq.watheq.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mahmoud.diab on 12/11/2017.
 */

public class ClientInfoModel implements Parcelable {
    private String name;
    private String civilRegistry;

    public ClientInfoModel() {
    }

    public ClientInfoModel(String name, String civilRegistry) {
        this.name = name;
        this.civilRegistry = civilRegistry;
    }

    protected ClientInfoModel(Parcel in) {
        name = in.readString();
        civilRegistry = in.readString();
    }

    public static final Creator<ClientInfoModel> CREATOR = new Creator<ClientInfoModel>() {
        @Override
        public ClientInfoModel createFromParcel(Parcel in) {
            return new ClientInfoModel(in);
        }

        @Override
        public ClientInfoModel[] newArray(int size) {
            return new ClientInfoModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCivilRegistry() {
        return civilRegistry;
    }

    public void setCivilRegistry(String civilRegistry) {
        this.civilRegistry = civilRegistry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(civilRegistry);
    }
}