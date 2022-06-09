package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Chapter {
    @SerializedName("id")
    private String id;

    @SerializedName("attributes")
    private ChapterAttribute attributes;

    public String getChapter() {
        return attributes.getChapter();
    }
    public String getChapterTitle() {
        return attributes.getTitle();
    }

    public String getChapterPublishDate() {
        String newDate = null;
        try {
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date dt = sd1.parse(attributes.getDate());
            SimpleDateFormat sd2 = new SimpleDateFormat("dd-MM-yyyy");
            newDate = sd2.format(dt);
        } catch (Exception e) {

        }

        return newDate;
    }

    public String getId() {
        return id;
    }

    public class ChapterAttribute {
        @SerializedName("title")
        private String title;
        @SerializedName("chapter")
        private String chapter;
        @SerializedName("readableAt")
        private String date;
        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getChapter() {
            return chapter;
        }

    }
}
