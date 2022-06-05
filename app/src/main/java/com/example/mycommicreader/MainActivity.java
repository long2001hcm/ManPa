package com.example.mycommicreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mycommicreader.model.LatestChapter;
import com.example.mycommicreader.model.Manga;
import com.example.mycommicreader.model.MangaBread;
import com.example.mycommicreader.modelview.MangaApiService;
import com.example.mycommicreader.databinding.ActivityMainBinding;
import com.example.mycommicreader.view.MangaAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progress;
    private ActivityMainBinding binding;
    private MangaAdapter mangaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 199, 249)));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        new MainActivity.GetManga().execute();



    }

    private class GetManga extends AsyncTask<Void, Void, Void> {
        List<Manga> mangaList = new ArrayList<Manga>();
        @Override
        protected  void onPreExecute () {
            progress = ProgressDialog.show(MainActivity.this, "Loading...", "Please Wait!!!");
        }

        @Override
        protected Void doInBackground (Void... devices) {

            try {
                Response<MangaBread> m = MangaApiService.apiService.getManga().execute();

                mangaList.addAll(m.body().getData());

                for (Manga manga:mangaList) {
                    Response<LatestChapter> l = MangaApiService.apiService.getLatestChapter(manga.getID()).execute();
                    try {
                        manga.setLatestChapter(l.body().getLatestChapter());
                    } catch (Exception e) {

                    }

                }

            } catch(Exception e) {
                Log.d("DEBUG", e.toString());
            }

            return null;
        }



        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);
            mangaAdapter = new MangaAdapter(mangaList);
            binding.rvMangas.setAdapter(mangaAdapter);
            binding.rvMangas.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            progress.dismiss();

        }
    }
}