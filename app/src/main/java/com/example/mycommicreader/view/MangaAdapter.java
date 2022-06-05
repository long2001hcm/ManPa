package com.example.mycommicreader.view;

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

import com.example.mycommicreader.R;
import com.example.mycommicreader.model.Manga;

import java.io.InputStream;
import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.ViewHolder>{
    private List<Manga> mangas;

    public MangaAdapter(List<Manga> dogs) {
        this.mangas = dogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mangalist_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mangas.get(position).getTitle());
        holder.chapter.setText(mangas.get(position).getLatestChapter());
        new DownloadImageTask(holder.imgAvt)
                .execute("https://uploads.mangadex.org/covers/" + mangas.get(position).getID() + "/" + mangas.get(position).getCoverFileName() + ".256.jpg");
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
    public int getItemCount() {
        return mangas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView chapter;
        public ImageView imgAvt;
        public ViewHolder(View view) {
            super(view);
            chapter = view.findViewById(R.id.manga_chapter);
            title = view.findViewById(R.id.manga_name);
            imgAvt = view.findViewById(R.id.imv_avt);
            imgAvt.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}
