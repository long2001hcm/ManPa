package com.example.mycommicreader.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.example.mycommicreader.MainActivity;
import com.example.mycommicreader.MangaDetail;
import com.example.mycommicreader.R;
import com.example.mycommicreader.model.Manga;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.ViewHolder>{
    private List<Manga> mangas;
    private OnNoteListener mOnNoteListener;
    private ImageLoader imgLoader;
    private Context context;
    public MangaAdapter(List<Manga> mangas, OnNoteListener onNoteListener, Context context) {
        this.mangas = mangas;
        this.mOnNoteListener = onNoteListener;
        this.context = context;
    }

    @NonNull
    @Override
    public MangaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mangalist_layout, parent, false);

        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = "https://uploads.mangadex.org/covers/" + mangas.get(position).getID() + "/" + mangas.get(position).getCoverFileName() + ".256.jpg";
        holder.title.setText(mangas.get(position).getTitle());
        //holder.chapter.setText(mangas.get(position).getLatestChapter());
        Picasso.get().load(url).into(holder.imgAvt);
//        new DownloadImageTask(holder.imgAvt)
//                .execute("https://uploads.mangadex.org/covers/" + mangas.get(position).getID() + "/" + mangas.get(position).getCoverFileName() + ".256.jpg");
    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }

    @Override
    public int getItemCount() {
        return mangas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView chapter;
        public ImageView imgAvt;
        OnNoteListener onNoteListener;
        public ViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            //chapter = view.findViewById(R.id.manga_chapter);
            title = view.findViewById(R.id.manga_name);
            imgAvt = view.findViewById(R.id.imv_avt);
            this.onNoteListener = onNoteListener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
