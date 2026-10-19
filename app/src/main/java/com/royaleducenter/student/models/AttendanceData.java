package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttendanceData {
    @SerializedName("records")
    public List<AttendanceRecord> records;

    @SerializedName("stats")
    public AttendanceStats stats;

    public static class AttendanceStats {
        @SerializedName("total") public int total;
        @SerializedName("present") public int present;
        @SerializedName("late") public int late;
        @SerializedName("absent") public int absent;
    }
}
