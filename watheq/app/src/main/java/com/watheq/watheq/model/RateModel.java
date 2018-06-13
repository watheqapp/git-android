package com.watheq.watheq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahmoud.diab on 2/13/2018.
 */

public class RateModel {
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("rate")
    @Expose
    private String rate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
