package com.example.notesapp.ui.noteItem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.domain.Note;
import com.example.notesapp.ui.MainActivity;
import com.example.notesapp.ui.list.NotesListFragment;
import com.example.notesapp.ui.list.NotesListPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class NoteItemFragmentEditable extends Fragment {

    public static final String ARG_NOTE = "note";

    private NotesListPresenter presenter;
    private TextInputEditText noteNameField;
    private TextInputEditText noteDescriptionField;

    public NoteItemFragmentEditable() {
        super(R.layout.fragment_note_item_editable);
    }

    public static NoteItemFragmentEditable newInstance(Note note) {
        NoteItemFragmentEditable fragment = new NoteItemFragmentEditable();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Note note = getArguments().getParcelable(ARG_NOTE);
        presenter = NotesListFragment.presenter;

        boolean isNewNote;

        MaterialButton saveButton = view.findViewById(R.id.save_button);
        noteNameField = view.findViewById(R.id.note_name_view);
        noteNameField.setText(note.getNoteName());
        noteDescriptionField = view.findViewById(R.id.note_description_view);
        noteDescriptionField.setText(note.getNoteDescription());
        MaterialTextView dateField = view.findViewById(R.id.note_date_created_view);

        isNewNote = note.getNoteName().equals("") && note.getNoteDescription().equals("") && note.getDate().equals("");

        MainActivity.displayDate(dateField);
        String date = dateField.getText().toString();

        saveButton.setOnClickListener(v -> {

            if (isNewNote) {
                presenter.saveNote(noteNameField.getText().toString(), date, noteDescriptionField.getText().toString());

            } else {
                presenter.rewriteNote(note, noteNameField.getText().toString(), date, noteDescriptionField.getText().toString());
            }

            Toast.makeText(getActivity(), "Saved!",
                    Toast.LENGTH_SHORT).show();

            getParentFragmentManager().popBackStack(MainActivity.backstackKeyNotesList, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });
    }
}
