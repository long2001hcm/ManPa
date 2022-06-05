package com.example.mycommicreader.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MangaBread {
    @SerializedName("data")
    private List<Manga> data;



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


