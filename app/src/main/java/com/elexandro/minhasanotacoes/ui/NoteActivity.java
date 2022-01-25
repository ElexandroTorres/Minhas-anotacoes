package com.elexandro.minhasanotacoes.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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

    private FloatingActionButton fabSaveNote;
    private EditText etTitle;
    private EditText etNote;
    private AlertDialog backDialog;
    private Note noteToUpdate;
    private boolean isToUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        findIds();
        setListeners();

        noteToUpdate = getIntent().getParcelableExtra("noteToUpdate");
        if(noteToUpdate != null) {
            isToUpdate = true;
            etTitle.setText(noteToUpdate.getTitle());
            etNote.setText(noteToUpdate.getDescription());
        }
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
                    Toast.makeText(getApplicationContext(), R.string.empty_note_toast, Toast.LENGTH_LONG).show();
                }
                else {
                    Note newNote;

                    if(isToUpdate) {
                        newNote = noteToUpdate;
                    }
                    else {
                        newNote = new Note();
                    }
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

                    if(!isToUpdate) {
                        Date date = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String strDate = dateFormat.format(date);

                        newNote.setDate(strDate);
                    }

                    Intent intent = new Intent();
                    if(isToUpdate) {
                        intent.putExtra("updatedNote", newNote);
                    }
                    else {
                        intent.putExtra("newNote", newNote);
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        String currentNoteTitle = etTitle.getText().toString();
        String currentNoteContent = etNote.getText().toString();
        if(!currentNoteTitle.isEmpty() || !currentNoteContent.isEmpty()) {
            if(noteToUpdate != null) {
                if(!currentNoteTitle.equals(noteToUpdate.getTitle())
                || !currentNoteContent.equals(noteToUpdate.getDescription())) {
                    backDialog();
                    backDialog.show();
                }
                else {
                    finish();
                }
            }
            else {
                backDialog();
                backDialog.show();
            }
        }
        else {
            finish();
        }
    }

    private void backDialog() {
        backDialog = new AlertDialog.Builder(NoteActivity.this).setTitle(R.string.back_alert_title)
                .setMessage(R.string.back_alert_message)
                .setPositiveButton(R.string.back_alert_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      backDialog.dismiss();
                      finish();
                    }
                })
                .setNegativeButton(R.string.back_alert_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }
}