package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("id") public int id;
    @SerializedName("user_id") public int userId;
    @SerializedName("student_code") public String studentCode;
    @SerializedName("first_name") public String firstName;
    @SerializedName("last_name") public String lastName;
    @SerializedName("gender") public String gender;
    @SerializedName("date_of_birth") public String dateOfBirth;
    @SerializedName("address") public String address;
    @SerializedName("city") public String city;
    @SerializedName("guardian_name") public String guardianName;
    @SerializedName("guardian_phone") public String guardianPhone;
    @SerializedName("guardian_email") public String guardianEmail;
    @SerializedName("school") public String school;
    @SerializedName("education_level") public String educationLevel;
    @SerializedName("emergency_contact") public String emergencyContact;
    @SerializedName("blood_group") public String bloodGroup;
    @SerializedName("nationality") public String nationality;
    @SerializedName("username") public String username;
    @SerializedName("email") public String email;
    @SerializedName("phone") public String phone;
    @SerializedName("full_name") public String fullName;
    @SerializedName("avatar") public String avatar;
    @SerializedName("status") public String status;
}
