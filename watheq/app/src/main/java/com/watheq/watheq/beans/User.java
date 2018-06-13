package com.watheq.watheq.beans;

/*
 * Created by Mahmoud on 3/13/2017.
 */

public class User {

    private String displayName;
    private String email;
    private String uid;
    private String device;
    private String photoUrl;
    private String instanceId;

    public User() {
    }

    public User(String displayName, String email, String uid, String device) {
        this.displayName = displayName;
        this.email = email;
        this.uid = uid;
        this.device = device;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
