package com.watheq.watheq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cost")
    @Expose
    private int cost;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("representativeName")
    @Expose
    private String representativeName;
    @SerializedName("nationalID")
    @Expose
    private int nationalID;
    @SerializedName("clientLat")
    @Expose
    private double clientLat;
    @SerializedName("clientLong")
    @Expose
    private double clientLong;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("lawyer")
    @Expose
    private Lawer lawyer;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("isInAcceptLawyerPeriod")
    @Expose
    private int isInAcceptLawyerPeriod;
    @SerializedName("accepted_at")
    @Expose
    private long acceptedAt;
    @SerializedName("created_at")
    @Expose
    private long createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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

    public int getNationalID() {
        return nationalID;
    }

    public void setNationalID(int nationalID) {
        this.nationalID = nationalID;
    }

    public double getClientLat() {
        return clientLat;
    }

    public void setClientLat(double clientLat) {
        this.clientLat = clientLat;
    }

    public double getClientLong() {
        return clientLong;
    }

    public void setClientLong(double clientLong) {
        this.clientLong = clientLong;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Lawer getLawyer() {
        return lawyer;
    }

    public void setLawyer(Lawer lawyer) {
        this.lawyer = lawyer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getIsInAcceptLawyerPeriod() {
        return isInAcceptLawyerPeriod;
    }

    public void setIsInAcceptLawyerPeriod(int isInAcceptLawyerPeriod) {
        this.isInAcceptLawyerPeriod = isInAcceptLawyerPeriod;
    }

    public long getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(long acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

}