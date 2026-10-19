package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class EnrollRequest {
    @SerializedName("batch_id")
    public int batchId;

    public EnrollRequest(int batchId) {
        this.batchId = batchId;
    }
}
