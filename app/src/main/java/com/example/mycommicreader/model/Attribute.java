package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Attribute {
    @SerializedName("title")
    private Title title;

    @SerializedName("tags")
    private ArrayList<Tag> tag;

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
    public String getTag() {
        String s = "";
        int i = 0;
        for(Tag t:tag) {
            i++;
            s = s + t.getName();
            if (i < tag.size()) {
                s = s + ", ";
            }
        }
        return s;
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

    public class Tag {
        @SerializedName("attributes")
        private TagAttribute attribute;
        public String getName() {
            return attribute.getName();
        }
        public class TagAttribute {
            @SerializedName("name")
            private TagName name;
            public String getName() {
                return name.getName();
            }
            public class TagName {
                @SerializedName("en")
                private String name;

                public String getName() {
                    return name;
                }
            }
        }
    }
}
