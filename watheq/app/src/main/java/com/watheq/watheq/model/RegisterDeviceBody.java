package com.watheq.watheq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahmoud.diab on 1/9/2018.
 */

public class RegisterDeviceBody {
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("firebaseToken")
    @Expose
    private String firebaseToken;

    public RegisterDeviceBody(String identifier, String firebaseToken) {
        this.identifier = identifier;
        this.firebaseToken = firebaseToken;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
