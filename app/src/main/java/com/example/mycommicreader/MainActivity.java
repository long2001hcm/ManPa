package com.example.mycommicreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mycommicreader.model.MangaBread;
import com.example.mycommicreader.view.MangaApiService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private MangaApiService mangaApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mangaApiService = new MangaApiService();
        mangaApiService.getMangas()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MangaBread>() {

                    @Override
                    public void onSuccess(@NonNull MangaBread mangaBread) {
                        String a = mangaBread.getData().get(0).getID();
                        Log.d("DEBUG1",a);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG1",e.getMessage());
                    }
                });
    }
}