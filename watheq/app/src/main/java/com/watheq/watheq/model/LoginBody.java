package com.watheq.watheq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginBody {

    @SerializedName("phone")
    @Expose
    private String phone;

    public LoginBody(String phone) {
        this.phone = phone;
    }

}