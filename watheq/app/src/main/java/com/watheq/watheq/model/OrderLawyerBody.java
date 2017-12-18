package com.watheq.watheq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderLawyerBody {

    @SerializedName("categoryId")
    @Expose
    private int categoryId;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("representativeName")
    @Expose
    private String representativeName;
    @SerializedName("clientNationalID")
    @Expose
    private String clientNationalID;
    @SerializedName("representativeNationalID")
    @Expose
    private String representativeNationalID;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public String getRepresentativeNationalID() {
        return representativeNationalID;
    }

    public void setRepresentativeNationalID(String representativeNationalID) {
        this.representativeNationalID = representativeNationalID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}