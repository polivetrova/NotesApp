package com.example.notesapp.ui.noteItem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.domain.Note1;
import com.example.notesapp.ui.MainActivity;
import com.example.notesapp.ui.list.NotesListFragment;
import com.example.notesapp.ui.list.NotesListPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteItemFragmentEditable extends Fragment {

    public static final String ARG_NOTE = "note";

    private NotesListPresenter presenter;
    private TextInputEditText noteNameField;
    private TextInputEditText noteDescriptionField;
    private String date;
    private MaterialTextView dateField;

    public NoteItemFragmentEditable() {
        super(R.layout.fragment_note_item_editable);
    }

    public static NoteItemFragmentEditable newInstance(Note1 note) {
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

        Note1 note = getArguments().getParcelable(ARG_NOTE);
        presenter = NotesListFragment.presenter;

        MaterialButton saveButton = view.findViewById(R.id.save_button);
        noteNameField = view.findViewById(R.id.note_name_view);
        noteNameField.setText(note.getNoteName1());
        noteDescriptionField = view.findViewById(R.id.note_description_view);
        noteDescriptionField.setText(note.getNoteDescription1());
        dateField = view.findViewById(R.id.note_date_created_view);
        displayDate();

        saveButton.setOnClickListener(v -> addNewNote());
    }

    public void displayDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT);
        date = dateFormat.format(calendar.getTime());
        dateField.setText(date);
    }

    public void addNewNote() {

        String noteName;
        String noteDescription;

        if (noteNameField.getText() != null) {
            noteName = noteNameField.getText().toString();
        } else {
            noteName = "";
        }
        if (noteDescriptionField.getText() != null) {
            noteDescription = noteDescriptionField.getText().toString();
        } else {
            noteDescription = "";
        }
        presenter.saveNote(noteName, date, noteDescription);

        Toast.makeText(getActivity(), "Saved!",
                Toast.LENGTH_LONG).show();

        getParentFragmentManager().popBackStack(MainActivity.backstackKeyNotesList, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}