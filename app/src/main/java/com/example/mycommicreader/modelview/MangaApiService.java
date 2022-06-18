package com.example.mycommicreader.modelview;

import com.example.mycommicreader.model.Chapter;
import com.example.mycommicreader.model.ChapterBread;
import com.example.mycommicreader.model.ChapterData;
import com.example.mycommicreader.model.LatestChapter;

import com.example.mycommicreader.model.MangaBread;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MangaApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy")
            .serializeNulls()
            .create();
    MangaApiService apiService = new Retrofit.Builder()
            .baseUrl("https://api.mangadex.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MangaApiService.class);

    @GET("manga?includes[]=cover_art&includes[]=author&order[latestUploadedChapter]=desc&contentRating[]=safe&limit=100")
    Call<MangaBread> getManga();

    @GET("manga?includes[]=cover_art&includes[]=author&order[followedCount]=desc&contentRating[]=safe&limit=100")
    Call<MangaBread> getPopularManga();

    @GET("manga?includes[]=cover_art&includes[]=author&order[followedCount]=desc&limit=100")
    Call<MangaBread> findManga(@Query("title") String title);

    @GET("manga?includes[]=cover_art&includes[]=author&order[latestUploadedChapter]=desc&limit=100")
    Call<MangaBread> getFollowedManga(@Query("ids[]") List<String> title);

    @GET("manga/{mangaID}/feed?translatedLanguage[]=en&order[chapter]=desc&limit=500")
    Call<ChapterBread> getChapter(@Path("mangaID") String mangaID);

    @GET("at-home/server/{chapterID}")
    Call<ChapterData> getChapterImage(@Path("chapterID") String chapterID);

    @GET("chapter?translatedLanguage[]=en&order[chapter]=desc&limit=1")
    Call<LatestChapter> getLatestChapter(@Query("manga") String id);
}