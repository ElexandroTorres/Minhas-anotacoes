package com.elexandro.minhasanotacoes.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private NotesAdapter adapter;
    private NotesDAO notesDAO;
    private RecyclerView rvNotesList;
    private FloatingActionButton fabAddNote;
    private List<Note> notes = new ArrayList<>();

    ActivityResultLauncher<Intent> noteResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK) {
                if(result.getData() != null && result.getData().getExtras().getParcelable("novaNota") != null) {
                    Note newNote = result.getData().getExtras().getParcelable("novaNota");
                    notes.add(newNote);
                    int index = notes.size() - 1;
                    adapter.notifyItemInserted(index);
                    notesDAO.save(newNote);
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
        adapter = new NotesAdapter(notes);

        for(int i = 0; i < notes.size(); i++) {
            Log.d("lista", notes.get(i).toString());
        }

        //notesDAO.delete(notes.get(0));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvNotesList.setLayoutManager(layoutManager);
        rvNotesList.setHasFixedSize(true);
        rvNotesList.setAdapter(adapter);

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

    private void findIds() {
        rvNotesList = findViewById(R.id.rv_notes_list);
        fabAddNote = findViewById(R.id.fab_add_note);
    }
}