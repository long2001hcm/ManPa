package com.example.mycommicreader;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface MangaAPI {
    @GET("manga")
    public Single<List<MangaBread>> getMangas();
}
