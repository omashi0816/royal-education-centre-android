package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class Exam {
    @SerializedName("id") public int id;
    @SerializedName("title") public String title;
    @SerializedName("description") public String description;
    @SerializedName("exam_date") public String examDate;
    @SerializedName("duration_minutes") public int durationMinutes;
    @SerializedName("total_marks") public int totalMarks;
    @SerializedName("passing_marks") public int passingMarks;
    @SerializedName("status") public String status;
    @SerializedName("batch_name") public String batchName;
    @SerializedName("subject_name") public String subjectName;
    @SerializedName("marks_obtained") public Double marksObtained;
    @SerializedName("percentage") public Double percentage;
    @SerializedName("grade") public String grade;
    @SerializedName("result_status") public String resultStatus;
    @SerializedName("remarks") public String remarks;

    public Exam() {}

    public Exam(String title, String subjectName, Double marksObtained, int totalMarks) {
        this.title = title;
        this.subjectName = subjectName;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
    }

    public boolean hasResult() {
        return resultStatus != null && !resultStatus.isEmpty() && !"null".equals(resultStatus);
    }
}
