package com.example.mycommicreader.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.example.mycommicreader.R;
import com.example.mycommicreader.model.Chapter;
import com.example.mycommicreader.model.Manga;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder>{
    private List<Chapter> chapters;
    private OnNoteListener mOnNoteListener;
    public ChapterAdapter(List<Chapter> chapters, OnNoteListener onNoteListener) {
        this.chapters = chapters;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ChapterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_chapter, parent, false);

        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String s = "";
        if (chapters.get(position).getChapter() == null)
            s = chapters.get(position).getChapterTitle();
        else
            s = chapters.get(position).getChapter();
        holder.chapter.setText(s);
    }



    @Override
    public int getItemCount() {
        return chapters.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView chapter;
        OnNoteListener onNoteListener;
        public ViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            chapter = view.findViewById(R.id.chapter);
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
