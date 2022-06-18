package com.example.mycommicreader;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String IDUser;
    private String DocumentID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_detail);
        binding = ActivityMangaDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
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
            IDUser = intent.getStringExtra("UserID");
            DocumentID = intent.getStringExtra("DocumentID");
            binding.title.setText(name + ".");
            binding.tag.setText("Tags: " + tag + ".");
            binding.author.setText("Author: " + author + ".");
            binding.type.setText("Demographic: " + type + ".");
            binding.status.setText("Status: " + status + ".");
            binding.year.setText("Year: " + year + ".");
            getSupportActionBar().setTitle("Manga details");
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
                    postDataStore(IDUser,id);
                } else {
                    binding.followButton.setText("Follow");
                    DeleteDataStore(IDUser,id);
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
        intent.putExtra("chapter", chapterList.get(position).getChapter());
        intent.putExtra("title", chapterList.get(position).getChapterTitle());
        startActivityForResult(intent, 2);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent();
                i.putExtra("DocID",DocumentID);
                setResult(RESULT_OK,i);
                finish();
                return true;
        }
        return true;
    }
    private void postDataStore(String idUser,String idManga){
        Map<String,Object> data = new HashMap<>();
        data.put("IDManga",idManga);
        firestore.collection(idUser)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DEBUG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        DocumentID = documentReference.getPath();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DEBUG", "Error adding document", e);
                    }
                });
    }
    private void DeleteDataStore(String idUser,String idManga){
        firestore.collection(IDUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getData().get("IDManga").toString() == idManga){
                                    document.getDocumentReference("IDManga").delete();
                                    Log.d("DEBUG","UnFollow");
                                }
                                Log.d("DEBUG", document.getId() + " => " + document.getData().get("IDManga").toString());
                            }
                        } else {
                            Log.w("DEBUG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}