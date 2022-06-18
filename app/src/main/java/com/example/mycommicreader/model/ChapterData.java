package com.example.mycommicreader.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChapterData implements Serializable {
    @SerializedName("baseUrl")
    private String baseUrl;

    @SerializedName("chapter")
    private ChapterImageData data;

    public List<String> getChapterImageUrl() {
        List<String> list = new ArrayList<>();
        for(String s: data.getData()) {
            list.add(baseUrl + s);
        }
        return list;
    }
    public class ChapterImageData {
        @SerializedName("hash")
        private String hash;

        @SerializedName("dataSaver")
        private List<String> fileName;

        public List<String> getData() {
            List<String> list = new ArrayList<>();
            for (String s: fileName) {
                list.add("/data-saver/" + hash + "/" + s);
            }
            return list;
        }
    }
}
