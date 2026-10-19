package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("device_name")
    public String deviceName;

    public LoginRequest(String username, String password, String deviceName) {
        this.username = username;
        this.password = password;
        this.deviceName = deviceName;
    }
}
