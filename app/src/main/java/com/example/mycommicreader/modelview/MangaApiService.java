package com.example.mycommicreader.modelview;

import com.example.mycommicreader.model.LatestChapter;

import com.example.mycommicreader.model.MangaBread;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MangaApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .create();
    MangaApiService apiService = new Retrofit.Builder()
            .baseUrl("https://api.mangadex.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MangaApiService.class);

    @GET("manga?includes[]=cover_art&includes[]=author&order[followedCount]=desc&contentRating[]=safe&limit=100")
    Call<MangaBread> getManga();

    @GET("chapter?translatedLanguage[]=en&order[chapter]=desc&limit=1")
    Call<LatestChapter> getLatestChapter(@Query("manga") String id);
}
