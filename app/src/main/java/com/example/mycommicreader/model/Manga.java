package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Manga {
    @SerializedName("id")
    private String id;
    @SerializedName("attributes")
    private Attribute attribute;
    @SerializedName("relationships")
    private List<Relationship> relationships;

    private String latestChapter = "0";

    public Manga(String id, Attribute attribute, List<Relationship> relationships) {
        this.id = id;
        this.attribute = attribute;
        this.relationships = relationships;
    }

    public String getLatestChapter() {
        return latestChapter;
    }

    public void setLatestChapter(String latestChapter) {
        this.latestChapter = latestChapter;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }
    public List<Relationship> getRelationships() {
        return this.relationships;
    }
    //get manga id
    public String getID() {
        return id;
    }
    //get manga title
    public String getTitle() {
        return attribute.getTitle();
    }
    //get manga type
    public String getType() {
        return attribute.getType();
    }
    //get manga title
    public String getStatus() {
        return attribute.getStatus();
    }
    //get manga title
    public String getYear() {
        return attribute.getYear();
    }
    //get manga description
    public String getDescription() {
        return attribute.getDescription();
    }
    //get cover art id
    public String getCoverFileName() {
        for (Relationship r: relationships) {
            if (r.getType().equals("cover_art"))
                return r.getFilename();
        }
        return "";
    }
    //get author
    public String getAuthor() {
        for (Relationship r: relationships) {
            if (r.getType().equals("author"))
                return r.getAuthor();
        }
        return "";
    }
}
