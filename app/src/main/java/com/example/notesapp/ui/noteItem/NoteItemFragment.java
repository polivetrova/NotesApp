package com.example.notesapp.ui.noteItem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteItemFragment extends Fragment {

    private static final String ARG_NOTE = "note";
    private static final String ARG_PRESENTER = "presenter";

    private NotesListPresenter presenter;
    private TextInputEditText noteNameField;
    private TextInputEditText noteDescriptionField;
    private MaterialTextView dateField;
    private String date;
//    private SimpleDateFormat dateFormat;

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
        date = note.getDate1();



        //displayDate(); - разобраться, как устанавливать текущую дату

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNote();
            }
        });


    }

    /*private void displayDate() {
        Calendar calendar = new Cale
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ROOT);
        date = dateFormat.format(calendar.getTime());
        dateField.setText(date);
    }*/


    private void addNewNote() {
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