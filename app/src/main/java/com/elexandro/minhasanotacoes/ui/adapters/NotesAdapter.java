package com.elexandro.minhasanotacoes.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elexandro.minhasanotacoes.R;
import com.elexandro.minhasanotacoes.model.Note;

import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private final List<Note> notes;

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(noteItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvNoteTitle.setText(note.getTitle());
        holder.tvNoteDate.setText(note.getDate());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTitle;
        TextView tvNoteDate;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tv_note_title);
            tvNoteDate = itemView.findViewById(R.id.tv_note_date);

        }
    }
}
