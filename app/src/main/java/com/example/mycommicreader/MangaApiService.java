package com.example.mycommicreader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
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
