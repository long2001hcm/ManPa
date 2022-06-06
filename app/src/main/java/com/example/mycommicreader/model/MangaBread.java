package com.example.mycommicreader.model;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MangaBread implements Serializable {
    @SerializedName("data")
    private ArrayList<Manga> data;

    @SerializedName("result")
    private String rs;



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


