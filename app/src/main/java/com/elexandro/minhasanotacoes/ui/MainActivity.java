package com.elexandro.minhasanotacoes.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.elexandro.minhasanotacoes.R;
import com.elexandro.minhasanotacoes.dao.NotesDAO;
import com.elexandro.minhasanotacoes.helpers.DBHelper;
import com.elexandro.minhasanotacoes.model.Note;
import com.elexandro.minhasanotacoes.ui.adapters.NotesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NotesAdapter.ItemNoteListener{

    private RecyclerView rvNotesList;
    private FloatingActionButton fabAddNote;

    private NotesAdapter adapter;
    private NotesDAO notesDAO;
    private List<Note> notes = new ArrayList<>();
    private AlertDialog deleteAllDialog;
    private AlertDialog deleteNoteDialog;

    private int indexToUpdate = -1;

    ActivityResultLauncher<Intent> noteResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK) {
                if(result.getData() != null && result.getData().getExtras().getParcelable("newNote") != null) {
                    Note newNote = result.getData().getExtras().getParcelable("newNote");
                    int index = notes.size();
                    notesDAO.save(newNote);
                    notes.add(0, newNote);
                    adapter.notifyItemInserted(0);
                }
                else if(result.getData() != null && result.getData().getExtras().getParcelable("updatedNote") != null) {
                    Note updatedNote = result.getData().getExtras().getParcelable("updatedNote");
                    notesDAO.update(updatedNote);
                    notes.set(indexToUpdate, updatedNote);
                    adapter.notifyItemChanged(indexToUpdate);
                    indexToUpdate = -1;
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        setListeners();

        notesDAO = new NotesDAO(getApplicationContext());
        notes =  notesDAO.listNotes();
        adapter = new NotesAdapter(notes, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvNotesList.setLayoutManager(layoutManager);
        rvNotesList.setHasFixedSize(true);
        rvNotesList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_delete_all) {
            createDeleteAllNotesDialog();
            deleteAllDialog.show();
        }
        return true;
    }

    private void findIds() {
        rvNotesList = findViewById(R.id.rv_notes_list);
        fabAddNote = findViewById(R.id.fab_add_note);
    }

    private void setListeners() {
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                noteResult.launch(intent);
            }
        });
    }

    private void createDeleteAllNotesDialog() {
        deleteAllDialog = new AlertDialog.Builder(MainActivity.this).setTitle(R.string.delete_all_notes_alert_tittle)
                .setMessage(R.string.delete_all_notes_alert_message)
                .setPositiveButton(R.string.delete_all_notes_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(notesDAO.deleteAll()) {
                            notes.clear();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, R.string.delete_all_notes_done, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, R.string.delete_all_notes_error, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.delete_all_notes_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    private void createDeleteNoteDialog(int position) {
        deleteNoteDialog = new AlertDialog.Builder(MainActivity.this).setTitle(R.string.delete_note_alert_title)
                .setMessage(R.string.delete_note_alert_message)
                .setPositiveButton(R.string.delete_all_notes_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(notesDAO.delete(notes.get(position))) {
                            notes.remove(position);
                            adapter.notifyItemRemoved(position);
                            Toast.makeText(MainActivity.this, R.string.delete_note_done, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, R.string.delete_note_error, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.delete_all_notes_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onItemClickListener(int position) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra("noteToUpdate", notes.get(position));
        indexToUpdate = position;
        noteResult.launch(intent);
    }

    @Override
    public void onItemLongClickListener(int position) {
        createDeleteNoteDialog(position);
        deleteNoteDialog.show();
    }
}