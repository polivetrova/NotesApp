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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotesListPresenter presenter = NotesListFragment.getPresenter();

        getSupportFragmentManager().setFragmentResultListener(NotesListFragment.KEY_NOTES_LIST, this, (requestKey, result) -> {

            Note1 note = result.getParcelable(NotesListFragment.ARG_NOTES_LIST);

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
        });

        // как передавать один и тот же презентер в оба фрагмента?

        //NotesListFragment listFragment = new NotesListFragment();

        addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(v -> {
            NoteItemFragment noteItemFragment = NoteItemFragment.newInstance(new Note1("", "", ""), presenter);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, noteItemFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}