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
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("letterDate")
    @Expose
    private String letterDate;
    @SerializedName("letterNumber")
    @Expose
    private String letterNumber;
    @SerializedName("marriageDate")
    @Expose
    private String marriageDate;
    @SerializedName("marriageTime")
    @Expose
    private String marriageTime;


    public String getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(String marriageDate) {
        this.marriageDate = marriageDate;
    }

    public String getMarriageTime() {
        return marriageTime;
    }

    public void setMarriageTime(String marriageTime) {
        this.marriageTime = marriageTime;
    }

    public String getLetterDate() {
        return letterDate;
    }

    public void setLetterDate(String letterDate) {
        this.letterDate = letterDate;
    }

    public String getLetterNumber() {
        return letterNumber;
    }

    public void setLetterNumber(String letterNumber) {
        this.letterNumber = letterNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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