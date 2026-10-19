package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class Enrollment {
    @SerializedName("id") public int id;
    @SerializedName("student_id") public int studentId;
    @SerializedName("batch_id") public int batchId;
    @SerializedName("enrolled_date") public String enrolledDate;
    @SerializedName("status") public String status;
    @SerializedName("final_grade") public String finalGrade;
    @SerializedName("batch_name") public String batchName;
    @SerializedName("start_date") public String startDate;
    @SerializedName("end_date") public String endDate;
    @SerializedName("course_name") public String courseName;
    @SerializedName("course_code") public String courseCode;
    @SerializedName("course_fee") public double courseFee;
    @SerializedName("monthly_fee") public double monthlyFee;

    public Enrollment() {}

    public Enrollment(String courseName, String batchName, String startDate, String endDate, String status) {
        this.courseName = courseName;
        this.batchName = batchName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
}
