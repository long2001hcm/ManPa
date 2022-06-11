package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatestChapter {
    @SerializedName("data")
    private List<Data> data;
    public String getLatestChapter() {
        return this.data.get(0).getLatestChapter();
    }
    public class Data {
        @SerializedName("attributes")
        private ChapterAttribute chapterAttribute;

        public String getLatestChapter() {
            return this.chapterAttribute.getLatestChapter();
        }
    }

    public class ChapterAttribute {
        @SerializedName("chapter")
        private String latestChapter;

        public String getLatestChapter() {
            return this.latestChapter;
        }
    }
}
