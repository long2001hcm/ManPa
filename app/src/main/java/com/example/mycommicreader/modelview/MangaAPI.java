package com.example.mycommicreader.modelview;

import com.example.mycommicreader.model.MangaBread;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface MangaAPI {
    @GET("manga")
    public Single<MangaBread> getMangas();
}
