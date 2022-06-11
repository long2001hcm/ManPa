package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChapterBread implements Serializable {
    @SerializedName("data")
    private List<Chapter> chapter;

    public List<Chapter> getChapter() {
        List<Chapter> newList = new ArrayList<>();
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        for (Chapter c: chapter) {
            if (map.get(c.getChapter()) == null) {
                map.put(c.getChapter(), true);
                newList.add(c);
            } else {

            }
        }
        return newList;
    }
}
