package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

public class Relationship {
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("attributes")
    private CoverAttribute attribute;

    public String getFilename() {
        return attribute.getFilename();
    }
    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }
}
