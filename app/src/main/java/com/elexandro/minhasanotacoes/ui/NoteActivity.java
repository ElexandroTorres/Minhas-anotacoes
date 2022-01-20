package com.elexandro.minhasanotacoes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.elexandro.minhasanotacoes.R;
import com.elexandro.minhasanotacoes.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    FloatingActionButton fabSaveNote;
    EditText etTitle;
    EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        findIds();
        setListeners();


    }

    private void findIds() {
        fabSaveNote = findViewById(R.id.fab_save_note);
        etTitle = findViewById(R.id.et_title);
        etNote = findViewById(R.id.et_note);
    }

    private void setListeners() {
        fabSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteDescription = etNote.getText().toString();
                String noteTitle = etTitle.getText().toString();

                if(noteDescription.equals("")) {
                    Toast.makeText(getApplicationContext(), "Insira algo na nota", Toast.LENGTH_LONG).show();
                }
                else {
                    Note newNote = new Note();
                    newNote.setDescription(noteDescription);

                    if(noteTitle.equals("")) {
                        if(noteDescription.length() > 16) {
                            newNote.setTitle(noteDescription.substring(0, 16) + "...");
                        }
                        else {
                            newNote.setTitle(noteDescription);
                        }
                    }
                    else {
                        newNote.setTitle(noteTitle);
                    }

                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String strDate = dateFormat.format(date);

                    newNote.setDate(strDate);

                    Intent intent = new Intent();
                    intent.putExtra("novaNota", newNote);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}