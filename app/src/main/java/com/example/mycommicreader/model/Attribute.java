package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attribute {
    @SerializedName("title")
    private Title title;

    @SerializedName("description")
    private Description description;

    @SerializedName("publicationDemographic")
    private String type;

    @SerializedName("status")
    private String status;

    @SerializedName("year")
    private String year;

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title.getTitle();
    }
    public String getDescription() {
        return description.getDescription();
    }
    public class Title {
        @SerializedName("en")
        private String en_title = null;

        @SerializedName("ja-ro")
        private String jaro_title = null;

        public String getTitle() {
            if (en_title!=null)
                return en_title;

            return jaro_title;
        }
    }

    public class Description {
        @SerializedName("en")
        private String description;
        public String getDescription() {
            return this.description;
        }
    }
}
