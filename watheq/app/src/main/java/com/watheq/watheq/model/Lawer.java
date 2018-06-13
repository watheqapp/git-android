package com.watheq.watheq.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.watheq.watheq.api.ClientServices;

/**
 * Created by mahmoud.diab on 1/9/2018.
 */

public class Lawer implements Parcelable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("active")
    @Expose
    private int active;
    @SerializedName("lawyerType")
    @Expose
    private String lawyerType;
    @SerializedName("IDCardFile")
    @Expose
    private String IDCardFile;
    @SerializedName("licenseFile")
    @Expose
    private String licenseFile;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("isCompleteProfile")
    @Expose
    private int isCompleteProfile;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("isCompleteFiles")
    private int isCompleteFiles;

    protected Lawer(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        email = in.readString();
        image = in.readString();
        phone = in.readString();
        if (in.readByte() == 0) {
            createdAt = null;
        } else {
            createdAt = in.readInt();
        }
        language = in.readString();
        active = in.readInt();
        lawyerType = in.readString();
        IDCardFile = in.readString();
        licenseFile = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        isCompleteProfile = in.readInt();
        token = in.readString();
        isCompleteFiles = in.readInt();
    }

    public static final Creator<Lawer> CREATOR = new Creator<Lawer>() {
        @Override
        public Lawer createFromParcel(Parcel in) {
            return new Lawer(in);
        }

        @Override
        public Lawer[] newArray(int size) {
            return new Lawer[size];
        }
    };

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getLawyerType() {
        return lawyerType;
    }

    public void setLawyerType(String lawyerType) {
        this.lawyerType = lawyerType;
    }

    public String getIDCardFile() {
        return IDCardFile;
    }

    public void setIDCardFile(String IDCardFile) {
        this.IDCardFile = IDCardFile;
    }

    public String getLicenseFile() {
        return licenseFile;
    }

    public void setLicenseFile(String licenseFile) {
        this.licenseFile = licenseFile;
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

    public int getIsCompleteFiles() {
        return isCompleteFiles;
    }

    public void setIsCompleteFiles(int isCompleteFiles) {
        this.isCompleteFiles = isCompleteFiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return ClientServices.baseUrl + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public int getIsCompleteProfile() {
        return isCompleteProfile;
    }

    public void setIsCompleteProfile(Integer isCompleteProfile) {
        this.isCompleteProfile = isCompleteProfile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(image);
        parcel.writeString(phone);
        if (createdAt == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(createdAt);
        }
        parcel.writeString(language);
        parcel.writeInt(active);
        parcel.writeString(lawyerType);
        parcel.writeString(IDCardFile);
        parcel.writeString(licenseFile);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeInt(isCompleteProfile);
        parcel.writeString(token);
        parcel.writeInt(isCompleteFiles);
    }
}
