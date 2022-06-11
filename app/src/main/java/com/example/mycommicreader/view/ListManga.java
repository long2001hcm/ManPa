package com.example.mycommicreader.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycommicreader.R;
import com.example.mycommicreader.model.MangaBread;
import com.example.mycommicreader.modelview.MangaApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListManga extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listmanga, container, false);
    }
}