package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class Payment {
    @SerializedName("id") public int id;
    @SerializedName("student_id") public int studentId;
    @SerializedName("enrollment_id") public Integer enrollmentId;
    @SerializedName("payment_type") public String paymentType;
    @SerializedName("amount") public double amount;
    @SerializedName("discount") public double discount;
    @SerializedName("final_amount") public double finalAmount;
    @SerializedName("payment_method") public String paymentMethod;
    @SerializedName("payment_date") public String paymentDate;
    @SerializedName("payment_month") public String paymentMonth;
    @SerializedName("status") public String status;
    @SerializedName("remarks") public String remarks;
    @SerializedName("batch_name") public String batchName;
    @SerializedName("course_name") public String courseName;

    public Payment() {}

    public Payment(String paymentType, double finalAmount, String paymentDate, String status, String paymentMethod) {
        this.paymentType = paymentType;
        this.finalAmount = finalAmount;
        this.paymentDate = paymentDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }
}
