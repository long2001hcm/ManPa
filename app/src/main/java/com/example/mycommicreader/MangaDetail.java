package com.example.mycommicreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.mycommicreader.databinding.ActivityMangaDetailBinding;
import com.example.mycommicreader.model.Chapter;
import com.example.mycommicreader.model.ChapterBread;
import com.example.mycommicreader.model.Manga;
import com.example.mycommicreader.model.MangaBread;
import com.example.mycommicreader.modelview.MangaApiService;
import com.example.mycommicreader.view.ChapterAdapter;
import com.example.mycommicreader.view.MangaAdapter;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MangaDetail extends AppCompatActivity implements ChapterAdapter.OnNoteListener {
    ProgressDialog progress;
    private ActivityMangaDetailBinding binding;
    List<Chapter> chapterList = new ArrayList<>();
    private ChapterAdapter chapterAdapter;
    private String id;
    private String name;
    private String coverFileName;
    private String tag;
    private String author;
    private String type;
    private String status;
    private String year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_detail);
        binding = ActivityMangaDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(view);
        chapterAdapter = new ChapterAdapter(chapterList, this);
        binding.followButton.setText("Follow");
        Intent intent = getIntent();

        if (intent != null) {
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            coverFileName = intent.getStringExtra("Cover");
            tag = intent.getStringExtra("tag");
            author = intent.getStringExtra("author");
            type = intent.getStringExtra("type");
            status = intent.getStringExtra("status");
            year = intent.getStringExtra("year");
            binding.title.setText(name + ".");
            binding.tag.setText("Tags: " + tag + ".");
            binding.author.setText("Author: " + author + ".");
            binding.type.setText("Demographic: " + type + ".");
            binding.status.setText("Status: " + status + ".");
            binding.year.setText("Year: " + year + ".");
            getSupportActionBar().setTitle("Manga Details");
//            new MangaDetail.DownloadImageTask(binding.cover)
//                    .execute("https://uploads.mangadex.org/covers/" + id + "/" + coverFileName + ".256.jpg");
            String url = "https://uploads.mangadex.org/covers/" + id + "/" + coverFileName + ".256.jpg";
            Picasso.get().load(url).into(binding.cover);
            new MangaDetail.GetChapters(id).execute();
        }
        binding.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.followButton.getText().toString() == "Follow") {
                    binding.followButton.setText("Followed");
                } else {
                    binding.followButton.setText("Follow");
                }
            }
        });
    }
    private class GetChapters extends AsyncTask<Void, Void, Void> {
        private String id;
        public GetChapters(String id) {
            this.id = id;
        }
        @Override
        protected  void onPreExecute () {
            progress = ProgressDialog.show(MangaDetail.this, "Loading...", "Please Wait!!!");
        }

        @Override
        protected Void doInBackground (Void... devices) {
            try {
//                get chapter list
                Response<ChapterBread> c = MangaApiService.apiService.getChapter(this.id).execute();
                chapterList.addAll(c.body().getChapter());

            } catch(Exception e) {
                Log.d("DEBUG", e.toString());
            }

            return null;
        }




        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);
            binding.chapterList.setAdapter(chapterAdapter);
            binding.chapterList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
            progress.dismiss();

        }
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(MangaDetail.this, ReadChapter.class);
        intent.putExtra("id", chapterList.get(position).getId());
        startActivityForResult(intent, 2);
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