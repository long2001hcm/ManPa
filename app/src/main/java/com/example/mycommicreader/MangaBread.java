package com.example.mycommicreader;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MangaBread {
    @SerializedName("data")
    private List<Manga> data;

    public class Manga {
        @SerializedName("id")
        private String id;

        public Manga(String id) {
            this.id = id;
        }
        public String getID() {
            return id;
        }
        public void setId() {
            this.id = id;
        }
    }

    public MangaBread(List<Manga> data) {
        this.data = data;
    }
    public List<Manga> getData() {
        return data;
    }

    public void setData(List<Manga> data) {
        this.data = data;
    }
}


