package com.example.mycommicreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
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
    List<String> followed = new ArrayList<>();
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
    private boolean deleted = false;
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
            followed = intent.getStringArrayListExtra("followed");
            binding.title.setText(name + ".");
            binding.tag.setText("Tags: " + tag + ".");
            binding.author.setText("Author: " + author + ".");
            binding.type.setText("Demographic: " + type + ".");
            binding.status.setText("Status: " + status + ".");
            binding.year.setText("Year: " + year + ".");
            if (followed.indexOf(id)>=0) {
                binding.followButton.setText("Followed");
            }
            getSupportActionBar().setTitle("Manga detail");
            String url = "https://uploads.mangadex.org/covers/" + id + "/" + coverFileName + ".256.jpg";
            Picasso.get().load(url).into(binding.cover);
            new MangaDetail.GetChapters(id).execute();
        }
        binding.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IDUser == null || IDUser.equals("")) {
                    LoginAlert();
                } else {
                    try {
                        if (binding.followButton.getText().toString() == "Follow") {
                            postDataStore(IDUser, id);
                            binding.followButton.setText("Followed");
                            deleted = false;
                        } else {
                            DeleteDataStore(IDUser, id);
                            binding.followButton.setText("Follow");
                            deleted = true;
                        }
                    } catch (Exception e) {

                    }
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
        intent.putExtra("position", position);
        ArrayList<String> l = new ArrayList<>();
        ArrayList<String> t = new ArrayList<>();
        ArrayList<String> n = new ArrayList<>();
        for (Chapter c:chapterList) {
            l.add(c.getId());
            t.add(c.getChapterTitle());
            n.add(c.getChapter());
        }
        intent.putStringArrayListExtra("chapterID", l);
        intent.putStringArrayListExtra("title", t);
        intent.putStringArrayListExtra("chapter", n);
        startActivityForResult(intent, 2);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case android.R.id.home:
                    Intent i = new Intent();
                    i.putExtra("DocID", DocumentID);
                    if (deleted) {
                        i.putExtra("mangaID", id);
                    } else {
                        i.putExtra("mangaID", "");
                    }
                    setResult(RESULT_OK, i);
                    finish();
                    return true;
            }
        } catch (Exception e) {
            Log.d("DEBUG", e.getMessage());
        }
        return true;
    }
    private void postDataStore(String idUser,String idManga){
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("IDManga", idManga);
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
        } catch (Exception e) {

        }
    }
    private void DeleteDataStore(String idUser,String idManga){
        firestore.collection(IDUser)
                .whereEqualTo("IDManga",idManga)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DEBUG", document.getId() + " => " + document.getData());
                                firestore.collection(IDUser)
                                        .document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("DEBUG", "Done delete!!");
                                            }
                                        });
                            }
                        } else {
                            Log.d("DEBUG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    void LoginAlert() {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MangaDetail.this);
        //dlgAlert.setTitle("Login");
        String s = "Login to use this feature (～￣▽￣)～";
        dlgAlert.setMessage(s);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}