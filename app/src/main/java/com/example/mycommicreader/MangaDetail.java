package com.example.mycommicreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.mycommicreader.databinding.ActivityMangaDetailBinding;
import com.example.mycommicreader.view.MangaAdapter;

import java.io.InputStream;

public class MangaDetail extends AppCompatActivity {
    private ActivityMangaDetailBinding binding;
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
            new MangaDetail.DownloadImageTask(binding.cover)
                    .execute("https://uploads.mangadex.org/covers/" + id + "/" + coverFileName + ".256.jpg");
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
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
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