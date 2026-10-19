package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class PaymentRequest {
    @SerializedName("payment_type")
    public String paymentType;

    @SerializedName("amount")
    public double amount;

    @SerializedName("payment_method")
    public String paymentMethod;

    @SerializedName("remarks")
    public String remarks;

    @SerializedName("enrollment_id")
    public int enrollmentId;

    public PaymentRequest(String paymentType, double amount, String paymentMethod, String remarks, int enrollmentId) {
        this.paymentType = paymentType;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.remarks = remarks;
        this.enrollmentId = enrollmentId;
    }
}
