package com.example.mycommicreader.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycommicreader.R;
import com.example.mycommicreader.model.Chapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private List<String> urls;
    public ImageAdapter(List<String> urls) {
        this.urls = urls;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter_image, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(urls.get(position)).into(holder.image_view);
    }



    @Override
    public int getItemCount() {
        return urls.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_view;
        public ViewHolder(View view) {
            super(view);
            image_view = view.findViewById(R.id.image_view);
        }
    }
}
