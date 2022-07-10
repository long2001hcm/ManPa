package com.example.mycommicreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
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
    private List<String> chapter;
    private List<String> title;
    private int position;
    private boolean hide = true;
    private List<String> chapterID = new ArrayList<>();
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

//        getSupportActionBar().hide();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            setSupportActionBar(binding.chapBar);
            getSupportActionBar().setTitle(null);
        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
        }
        Intent intent = getIntent();

        if (intent != null) {
            id = intent.getStringExtra("id");
            chapter = intent.getStringArrayListExtra("chapter");
            title = intent.getStringArrayListExtra("title");
            position = intent.getIntExtra("position", 0);
            chapterID = intent.getStringArrayListExtra("chapterID");
            if (chapter.get(position) != null) {
                binding.chapter.setText(chapter.get(position));
            } else {
                binding.chapter.setText(title.get(position));
            }
            new ReadChapter.GetImages(chapterID.get(position)).execute();
        }

        binding.image.setOnTouchListener(new OnSwipeTouchListener(ReadChapter.this) {
            public void onSwipeTop() {
                getSupportActionBar().hide();
            }
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {

            }
            public void onSwipeBottom() {
                getSupportActionBar().show();
            }

        });

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
                listUrl.removeAll(listUrl);
                listUrl.addAll(c.body().getChapterImageUrl());

            } catch(Exception e) {
                Log.d("DEBUG", e.toString());
            }

            return null;
        }




        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);
            binding.notify.setText("");
            if (!isNetworkAvailable()) {
                binding.notify.setText("No internet connection ￣へ￣");
            } else if (listUrl.size() == 0) {
                binding.notify.setText("Can't load this chapter (っ °Д °;)っ");
            }
            binding.image.setAdapter(imageAdapter);
            binding.image.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
            progress.dismiss();

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chap_menu, menu);
        try {
            MenuItem menuItem = menu.findItem(R.id.back);
            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    finish();
                    return false;
                }
            });

            MenuItem menuItem1 = menu.findItem(R.id.pre_chap);
            menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (position != chapterID.size()-1) {
                        position = position + 1;
                        if (chapter.get(position) != null) {
                            binding.chapter.setText(chapter.get(position));
                        } else {
                            binding.chapter.setText(title.get(position));
                        }
                        new ReadChapter.GetImages(chapterID.get(position)).execute();
                    }
                    return false;
                }
            });

            MenuItem menuItem2 = menu.findItem(R.id.next_chap);
            menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (position != 0) {
                        position = position - 1;
                        if (chapter.get(position) != null) {
                            binding.chapter.setText(chapter.get(position));
                        } else {
                            binding.chapter.setText(title.get(position));
                        }
                        new ReadChapter.GetImages(chapterID.get(position)).execute();
                    }
                    return false;
                }
            });


        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
        }
        return true;
    }
}