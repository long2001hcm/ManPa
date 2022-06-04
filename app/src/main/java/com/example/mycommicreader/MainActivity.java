package com.example.mycommicreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MangaApiService.apiService.getManga().enqueue(new Callback<MangaBread>() {
            @Override
            public void onResponse(Call<MangaBread> call, Response<MangaBread> response) {
                Log.d("DEBUG", response.body().getData().size() + "");
            }

            @Override
            public void onFailure(Call<MangaBread> call, Throwable t) {

            }
        });
    }
}