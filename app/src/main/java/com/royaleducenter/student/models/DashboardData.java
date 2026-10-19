package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardData {
    @SerializedName("stats")
    public Stats stats;

    @SerializedName("enrollments")
    public List<Enrollment> enrollments;

    @SerializedName("today_classes")
    public List<TimetableSlot> todayClasses;

    @SerializedName("recent_results")
    public List<Exam> recentResults;

    @SerializedName("notices")
    public List<Notice> notices;

    public static class Stats {
        @SerializedName("active_enrollments") public int activeEnrollments;
        @SerializedName("total_enrollments") public int totalEnrollments;
        @SerializedName("attendance_present") public int attendancePresent;
        @SerializedName("attendance_late") public int attendanceLate;
        @SerializedName("attendance_absent") public int attendanceAbsent;
        @SerializedName("attendance_total") public int attendanceTotal;
        @SerializedName("pending_payments") public int pendingPayments;
        @SerializedName("pending_amount") public double pendingAmount;
    }
}
