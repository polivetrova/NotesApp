package com.example.notesapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.notesapp.domain.Note1;
import com.example.notesapp.ui.list.NotesListFragment;
import com.example.notesapp.ui.list.NotesListPresenter;
import com.example.notesapp.ui.noteItem.NoteItemFragment;
import com.example.notesapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNoteButton;

    NotesListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // как передавать один и тот же презентер в оба фрагмента?

        NotesListFragment listFragment = new NotesListFragment();

        addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(v -> {
            NoteItemFragment noteItemFragment = NoteItemFragment.newInstance(new Note1( "","",""), listFragment.getPresenter());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, noteItemFragment)
                    .addToBackStack(null)
                    .commit();

            //почему падает при сохранении новой заметки?
        });
    }
}