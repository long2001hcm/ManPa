package com.example.mycommicreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mycommicreader.model.MangaBread;
import com.example.mycommicreader.modelview.MangaApiService;

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