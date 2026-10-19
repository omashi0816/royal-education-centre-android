package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

    @SerializedName("token")
    public String token;

    @SerializedName("expires_at")
    public String expiresAt;

    @SerializedName("user")
    public UserInfo user;

    @SerializedName("student")
    public StudentInfo student;

    public static class UserInfo {
        @SerializedName("id") public int id;
        @SerializedName("username") public String username;
        @SerializedName("full_name") public String fullName;
        @SerializedName("email") public String email;
        @SerializedName("phone") public String phone;
        @SerializedName("avatar") public String avatar;
        @SerializedName("role") public String role;
    }

    public static class StudentInfo {
        @SerializedName("id") public int id;
        @SerializedName("student_code") public String studentCode;
        @SerializedName("first_name") public String firstName;
        @SerializedName("last_name") public String lastName;
        @SerializedName("gender") public String gender;
        @SerializedName("date_of_birth") public String dateOfBirth;
        @SerializedName("address") public String address;
        @SerializedName("city") public String city;
        @SerializedName("school") public String school;
        @SerializedName("education_level") public String educationLevel;
        @SerializedName("guardian_name") public String guardianName;
        @SerializedName("guardian_phone") public String guardianPhone;
    }
}
