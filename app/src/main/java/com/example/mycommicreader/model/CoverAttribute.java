package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

public class CoverAttribute {
    @SerializedName("fileName")
    private String filename;
    public String getFilename() {
        return this.filename;
    }
}
