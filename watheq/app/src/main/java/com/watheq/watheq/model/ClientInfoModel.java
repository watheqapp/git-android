package com.watheq.watheq.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mahmoud.diab on 12/11/2017.
 */

public class ClientInfoModel implements Parcelable {
    private String name;
    private String civilRegistry;
    private String representativeName;
    private String clientNationalID;
    private int id;
    private int cost;
    private int deliveryToHomeFees;
    private String title;
    private boolean isAuth;
    private String deliveryPlace;

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private boolean isHasNoSubs;

    public boolean isHasNoSubs() {
        return isHasNoSubs;
    }

    public void setHasNoSubs(boolean hasNoSubs) {
        isHasNoSubs = hasNoSubs;
    }


    public int getDeliveryToHomeFees() {
        return deliveryToHomeFees;
    }

    public void setDeliveryToHomeFees(int deliveryToHomeFees) {
        this.deliveryToHomeFees = deliveryToHomeFees;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getClientNationalID() {
        return clientNationalID;
    }

    public void setClientNationalID(String clientNationalID) {
        this.clientNationalID = clientNationalID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
