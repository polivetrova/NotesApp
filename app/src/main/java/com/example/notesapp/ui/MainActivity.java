package com.example.notesapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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


    // почему в лендскейпе отображается новый список?
    // в лендскейпе заметка после сохранения не отображается в списке сразу
    // научиться сохранять изменения в существующей заметке, а не создавать из изменений новую
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = NotesListFragment.getPresenter();

        getSupportFragmentManager().setFragmentResultListener(NotesListFragment.KEY_NOTES_LIST, this, (requestKey, result) -> {
            showNoteItemFragment(presenter, result.getParcelable(NotesListFragment.ARG_NOTES_LIST));
        });

        addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(v -> showNoteItemFragment(presenter, new Note1("", "", "")));
    }

    private void showNoteItemFragment(NotesListPresenter presenter, Note1 note) {
        NoteItemFragment noteItemFragment = NoteItemFragment.newInstance(note, presenter);
        FragmentManager manager = getSupportFragmentManager();
        boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);

        if (isLandscape) {
            manager.beginTransaction()
                    .replace(R.id.fragment_notes_item_container, noteItemFragment)
                    .commit();
        } else {
            manager.beginTransaction()
                    .replace(R.id.fragment_container, noteItemFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}