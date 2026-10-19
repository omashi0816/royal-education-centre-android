package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class AttendanceRecord {
    @SerializedName("id") public int id;
    @SerializedName("enrollment_id") public int enrollmentId;
    @SerializedName("date") public String date;
    @SerializedName("status") public String status;
    @SerializedName("batch_name") public String batchName;
    @SerializedName("subject_name") public String subjectName;

    public AttendanceRecord() {}

    public AttendanceRecord(String date, String subjectName, String status) {
        this.date = date;
        this.subjectName = subjectName;
        this.status = status;
    }
}
