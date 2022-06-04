package com.example.mycommicreader.modelview;

import com.example.mycommicreader.model.MangaBread;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public interface MangaApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .create();
    MangaApiService apiService = new Retrofit.Builder()
            .baseUrl("https://api.mangadex.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MangaApiService.class);
    @GET("manga")
    Call<MangaBread> getManga();
}
