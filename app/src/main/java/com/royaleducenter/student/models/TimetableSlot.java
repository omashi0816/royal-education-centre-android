package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class TimetableSlot {
    @SerializedName("id") public int id;
    @SerializedName("batch_id") public int batchId;
    @SerializedName("subject_id") public int subjectId;
    @SerializedName("teacher_id") public int teacherId;
    @SerializedName("day_of_week") public String dayOfWeek;
    @SerializedName("start_time") public String startTime;
    @SerializedName("end_time") public String endTime;
    @SerializedName("room") public String room;
    @SerializedName("subject_name") public String subjectName;
    @SerializedName("subject_code") public String subjectCode;
    @SerializedName("first_name") public String teacherFirstName;
    @SerializedName("last_name") public String teacherLastName;
    @SerializedName("batch_name") public String batchName;
    @SerializedName("course_name") public String courseName;
    
    // For mock data
    public String teacherName;
    public String timeString;

    public TimetableSlot() {}

    public TimetableSlot(String subjectName, String teacherName, String timeString, String room) {
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.timeString = timeString;
        this.room = room;
    }

    public TimetableSlot(String subjectName, String teacherName, String timeString, String room, String dayOfWeek) {
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.timeString = timeString;
        this.room = room;
        this.dayOfWeek = dayOfWeek;
    }

    public String getTeacherName() {
        if (teacherName != null) return teacherName;
        return (teacherFirstName != null ? teacherFirstName : "") + " " +
               (teacherLastName != null ? teacherLastName : "");
    }

    public String getFormattedTime() {
        if (timeString != null) return timeString;
        if (startTime == null) return "";
        String start = startTime.length() >= 5 ? startTime.substring(0, 5) : startTime;
        String end = endTime != null && endTime.length() >= 5 ? endTime.substring(0, 5) : (endTime != null ? endTime : "");
        return start + (end.isEmpty() ? "" : " - " + end);
    }

    public String getCapitalizedDay() {
        if (dayOfWeek == null || dayOfWeek.isEmpty()) return "";
        return dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
    }
}
