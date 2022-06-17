package com.example.mycommicreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.mycommicreader.databinding.ActivityMangaDetailBinding;
import com.example.mycommicreader.databinding.ActivityReadChapterBinding;
import com.example.mycommicreader.model.ChapterBread;
import com.example.mycommicreader.model.ChapterData;
import com.example.mycommicreader.modelview.MangaApiService;
import com.example.mycommicreader.view.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ReadChapter extends AppCompatActivity {
    private ActivityReadChapterBinding binding;
    ProgressDialog progress;
    private String id;
    private String chapter;
    private String title;
    private ImageAdapter imageAdapter;
    List<String> listUrl = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_chapter);

        binding = ActivityReadChapterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        imageAdapter = new ImageAdapter(listUrl);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        getSupportActionBar().hide();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if (intent != null) {
            id = intent.getStringExtra("id");
            chapter = intent.getStringExtra("chapter");
            title = intent.getStringExtra("title");
            if (chapter == null) {
                chapter = "";
            } else {
                chapter = "Chapter " + chapter;
            }

            if (title == null) {
                title = "";
            }
            
            String s = "";
            if (title != "" && chapter != "") {
                s = chapter + ". " + title;
            } else {
                s = chapter + title;
            }
            getSupportActionBar().setTitle(s);
            new ReadChapter.GetImages(id).execute();
        }

    }
    private class GetImages extends AsyncTask<Void, Void, Void> {
        private String id;
        public GetImages(String id) {
            this.id = id;
        }
        @Override
        protected  void onPreExecute () {
            progress = ProgressDialog.show(ReadChapter.this, "Loading...", "Please Wait!!!");
        }

        @Override
        protected Void doInBackground (Void... devices) {
            try {
                Response<ChapterData> c = MangaApiService.apiService.getChapterImage(id).execute();
                listUrl.addAll(c.body().getChapterImageUrl());

            } catch(Exception e) {
                Log.d("DEBUG", e.toString());
            }

            return null;
        }




        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);
            binding.image.setAdapter(imageAdapter);
            binding.image.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            progress.dismiss();

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}