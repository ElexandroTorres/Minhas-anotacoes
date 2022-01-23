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
    private ItemNoteListener itemListener;

    public NotesAdapter(List<Note> notes, ItemNoteListener itemListener) {
        this.notes = notes;
        this.itemListener = itemListener;
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(noteItem, itemListener);
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

    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView tvNoteTitle;
        TextView tvNoteDate;
        ItemNoteListener itemNListener;

        public NotesViewHolder(@NonNull View itemView, ItemNoteListener itemListener) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tv_note_title);
            tvNoteDate = itemView.findViewById(R.id.tv_note_date);
            this.itemNListener = itemListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemNListener.onItemClickListener(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            itemNListener.onItemLongClickListener(getAdapterPosition());
            return true;
        }
    }

    public interface ItemNoteListener {
        void onItemClickListener(int position);
        void onItemLongClickListener(int posiiton);
    }
}
