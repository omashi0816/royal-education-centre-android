package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Course {
    @SerializedName("batch_id") public int batchId;
    @SerializedName("batch_name") public String batchName;
    @SerializedName("start_date") public String startDate;
    @SerializedName("end_date") public String endDate;
    @SerializedName("max_students") public int maxStudents;
    @SerializedName("batch_status") public String batchStatus;
    @SerializedName("course_id") public int courseId;
    @SerializedName("course_name") public String courseName;
    @SerializedName("course_code") public String courseCode;
    @SerializedName("course_description") public String courseDescription;
    @SerializedName("course_fee") public double courseFee;
    @SerializedName("registration_fee") public double registrationFee;
    @SerializedName("monthly_fee") public double monthlyFee;
    @SerializedName("duration_months") public int durationMonths;
    @SerializedName("enrolled_count") public int enrolledCount;
    @SerializedName("timetable") public List<TimetableSlot> timetable;

    public Course() {}

    public Course(int batchId, String courseName, String batchName, double courseFee, double monthlyFee, int enrolledCount, int maxStudents) {
        this.batchId = batchId;
        this.courseName = courseName;
        this.batchName = batchName;
        this.courseFee = courseFee;
        this.monthlyFee = monthlyFee;
        this.enrolledCount = enrolledCount;
        this.maxStudents = maxStudents;
    }
}
