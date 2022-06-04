package com.example.mycommicreader.view;

import com.example.mycommicreader.model.MangaBread;
import com.example.mycommicreader.modelview.MangaAPI;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MangaApiService {
    private static final String BASE_URL = "https://api.mangadex.org";
    private static MangaAPI mangaAPI;

    public MangaApiService(){
        if(mangaAPI == null){
            mangaAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build().create(MangaAPI.class);
        }
    }
    public Single<MangaBread> getMangas(){
        return  mangaAPI.getMangas();
    }
}
