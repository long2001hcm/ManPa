package com.example.mycommicreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mycommicreader.model.Chapter;
import com.example.mycommicreader.model.ChapterBread;
import com.example.mycommicreader.model.Manga;
import com.example.mycommicreader.model.MangaBread;

import com.example.mycommicreader.modelview.MangaApiService;
import com.example.mycommicreader.databinding.ActivityMainBinding;
import com.example.mycommicreader.view.MangaAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MangaAdapter.OnNoteListener {

    ProgressDialog progress;
    private ActivityMainBinding binding;
    private MangaAdapter mangaAdapter;
    List<Manga> mangaList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 199, 249)));
        getSupportActionBar().setTitle("Popular manga");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        mangaAdapter = new MangaAdapter(mangaList, this);
        setContentView(view);
        new MainActivity.GetManga("").execute();


    }

    private class GetManga extends AsyncTask<Void, Void, Void> {
        private String title;
        public GetManga(String title) {
            this.title = title;
        }
        @Override
        protected  void onPreExecute () {
            progress = ProgressDialog.show(MainActivity.this, "Loading...", "Please Wait!!!");
        }

        @Override
        protected Void doInBackground (Void... devices) {


            try {
                Response<MangaBread> m;
                if (title == "") {
                    m = MangaApiService.apiService.getManga().execute();
                } else {
                    m = MangaApiService.apiService.findManga(title).execute();
                }

                mangaList.removeAll(mangaList);
                mangaList.addAll(m.body().getData());

//                get followed manga
//                List<String> listMangaID = new ArrayList<>();
//                list.add("a96676e5-8ae2-425e-b549-7f15dd34a6d8");
//                list.add("37f5cce0-8070-4ada-96e5-fa24b1bd4ff9");
//                m = MangaApiService.apiService.getFollowedManga(listMangaID).execute();

//                get chapter list
//                Response<ChapterBread> c = MangaApiService.apiService.getChapter("6b1eb93e-473a-4ab3-9922-1a66d2a29a4a").execute();
//                List<Chapter> listChapter = c.body().getChapter();
//                for (Chapter l:listChapter) {
//                    Log.d("DEBUG", l.getChapter() + " " + l.getChapterTitle() + " " + l.getChapterPublishDate());
//                }

//                get latest chapter
//                for (Manga manga:mangaList) {
//                    Response<LatestChapter> l = MangaApiService.apiService.getLatestChapter(manga.getID()).execute();
//                    try {
//                        manga.setLatestChapter(l.body().getLatestChapter());
//                    } catch (Exception e) {
//
//                    }
//
//                }

            } catch(Exception e) {
                Log.d("DEBUG", e.toString());
            }

            return null;
        }




        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);
            binding.rvMangas.setAdapter(mangaAdapter);
            binding.rvMangas.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            progress.dismiss();

        }
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(MainActivity.this, MangaDetail.class);
        Manga m = mangaList.get(position);
        intent.putExtra("id", m.getID());
        intent.putExtra("name", m.getTitle());
        intent.putExtra("tag", m.getTag());
        intent.putExtra("Cover", m.getCoverFileName());
        intent.putExtra("author", m.getAuthor());
        intent.putExtra("type", m.getType());
        intent.putExtra("status", m.getStatus());
        intent.putExtra("year", m.getYear());
        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new MainActivity.GetManga(query).execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                new MainActivity.GetManga("").execute();
                return true;
            }
        });
        return true;
    }
}