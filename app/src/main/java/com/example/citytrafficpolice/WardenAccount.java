package com.example.citytrafficpolice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WardenAccount {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("CNIC")
    @Expose
    private String CNIC;

    @SerializedName("jwtToken")
    @Expose
    private String jwtToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCNIC() {
        return CNIC;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
