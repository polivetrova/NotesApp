package com.example.notesapp.ui.noteItem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.domain.Note1;
import com.example.notesapp.ui.list.NotesListPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteItemFragment extends Fragment {

    private static final String ARG_NOTE = "note";
    public static final String ARG_PRESENTER = "presenter";

    private NotesListPresenter presenter;
    private TextInputEditText noteNameField;
    private TextInputEditText noteDescriptionField;
    private MaterialTextView dateField;
    private String date;

    public NoteItemFragment() {
        super(R.layout.fragment_note_item);
    }

    public static NoteItemFragment newInstance(Note1 note, NotesListPresenter presenter) {
        NoteItemFragment fragment = new NoteItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        args.putParcelable(ARG_PRESENTER, presenter);
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
        presenter = getArguments().getParcelable(ARG_PRESENTER);

        MaterialButton saveButton = view.findViewById(R.id.save_button);

        noteNameField = view.findViewById(R.id.note_name);
        noteNameField.setText(note.getNoteName1());
        noteDescriptionField = view.findViewById(R.id.note_description);
        noteDescriptionField.setText(note.getNoteDescription1());
        dateField = view.findViewById(R.id.note_date_created);
        dateField.setText(note.getDate1());
        if(!note.getDate1().equals("")){
            date = note.getDate1();
        } else {
            date = displayDate();
        }


        //displayDate(); - разобраться, как устанавливать текущую дату

        saveButton.setOnClickListener(v -> addNewNote());
    }

    private String displayDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT);
        date = dateFormat.format(calendar.getTime());
        dateField.setText(date);
        return date;
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
    }
}