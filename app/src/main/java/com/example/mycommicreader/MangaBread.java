package com.example.mycommicreader;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MangaBread implements Serializable {
    @SerializedName("data")
    private ArrayList<Manga> data;

    @SerializedName("result")
    private String rs;

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

    public MangaBread(ArrayList<Manga> data) {
        this.data = data;
    }
    public List<Manga> getData() {
        return data;
    }

    public void setData(ArrayList<Manga> data) {
        this.data = data;
    }
}


