package com.example.notesapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Parcelable;

import com.example.notesapp.domain.Note1;
import com.example.notesapp.ui.list.NotesListFragment;
import com.example.notesapp.ui.list.NotesListPresenter;
import com.example.notesapp.ui.noteItem.NoteItemFragmentEditable;
import com.example.notesapp.R;
import com.example.notesapp.ui.noteItem.NoteItemFragmentUneditable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNoteButton;
    NotesListPresenter presenter;
    FragmentManager manager = getSupportFragmentManager();



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
            if (result.getBoolean(NotesListFragment.FRAGMENT_TYPE)) {
                showEditableNoteItemFragment(presenter, result.getParcelable(NotesListFragment.ARG_NOTES_LIST));
            } else if (!result.getBoolean(NotesListFragment.FRAGMENT_TYPE)) {
                showUneditableNoteItemFragment(presenter, result.getParcelable(NotesListFragment.ARG_NOTES_LIST));
            }
        });

        getSupportFragmentManager().setFragmentResultListener(NoteItemFragmentUneditable.KEY_NOTE_ITEM,this, ((requestKey, result) -> {
            showEditableNoteItemFragment(presenter, result.getParcelable(NoteItemFragmentUneditable.ARG_NOTE_2));
        }));

        addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(v -> showEditableNoteItemFragment(presenter, new Note1("", "", "")));
    }

    private void showUneditableNoteItemFragment(NotesListPresenter presenter, Note1 note) {
        NoteItemFragmentUneditable noteItemFragmentUneditable = NoteItemFragmentUneditable.newInstance(note, presenter);
        startTransaction(noteItemFragmentUneditable);
    }

    private void showEditableNoteItemFragment(NotesListPresenter presenter, Note1 note) {
        NoteItemFragmentEditable noteItemFragmentEditable = NoteItemFragmentEditable.newInstance(note, presenter);
        startTransaction(noteItemFragmentEditable);
    }

    private void startTransaction(Fragment fragment){
        boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);

        if (isLandscape) {
            manager.beginTransaction()
                    .replace(R.id.fragment_notes_item_container, fragment)
                    .commit();
        } else {
            manager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}