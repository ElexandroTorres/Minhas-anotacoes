package com.elexandro.minhasanotacoes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private NotesAdapter adapter;
    private NotesDAO notesDAO;
    private RecyclerView rvNotesList;
    private FloatingActionButton fabAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        setListeners();


        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = dateFormat.format(date);

        /*
        Toast.makeText(getApplicationContext(), strDate
                , Toast.LENGTH_LONG).show();
        */

        notesDAO = new NotesDAO(getApplicationContext());
        List<Note> notes =  notesDAO.listNotes();
        adapter = new NotesAdapter(notes);

        //Toast.makeText(getApplicationContext(), notesDAO.listNotes().size(), Toast.LENGTH_SHORT).show();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvNotesList.setLayoutManager(layoutManager);
        rvNotesList.setHasFixedSize(true);
        rvNotesList.setAdapter(adapter);





    }

    private void setListeners() {
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findIds() {
        rvNotesList = findViewById(R.id.rv_notes_list);
        fabAddNote = findViewById(R.id.fab_add_note);
    }
}