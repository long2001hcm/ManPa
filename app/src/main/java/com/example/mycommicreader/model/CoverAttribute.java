package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

public class CoverAttribute {
    @SerializedName("fileName")
    private String filename;

    @SerializedName("name")
    private String authorName;

    public String getAuthorName() {
        return this.authorName;
    }

    public String getFilename() {
        return this.filename;
    }
}
