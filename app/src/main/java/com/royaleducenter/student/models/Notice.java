package com.royaleducenter.student.models;

import com.google.gson.annotations.SerializedName;

public class Notice {
    @SerializedName("id") public int id;
    @SerializedName("title") public String title;
    @SerializedName("content") public String content;
    @SerializedName("target_role") public String targetRole;
    @SerializedName("posted_at") public String postedAt;
    @SerializedName("is_pinned") public boolean isPinned;

    public Notice() {}

    public Notice(String title, String content, String postedAt) {
        this.title = title;
        this.content = content;
        this.postedAt = postedAt;
    }

    public Notice(String title, String content, String postedAt, boolean isPinned) {
        this.title = title;
        this.content = content;
        this.postedAt = postedAt;
        this.isPinned = isPinned;
    }
}
