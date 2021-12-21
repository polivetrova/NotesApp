package com.example.notesapp.ui.noteItem;

import static com.example.notesapp.ui.list.NotesListFragment.FRAGMENT_TYPE;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.example.notesapp.R;
import com.example.notesapp.domain.Note1;
import com.example.notesapp.ui.list.NotesListPresenter;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteItemFragmentUneditable extends Fragment {

    public static final String ARG_NOTE_2= "note 2";
    public static final String KEY_NOTE_ITEM = "KEY_NOTE_ITEM";

    public static final String ARG_PRESENTER_2 = "presenter 2"; //?
    private MaterialTextView dateTextView;

    public NoteItemFragmentUneditable() {
        super(R.layout.fragment_note_item_uneditable);
    }

    public static NoteItemFragmentUneditable newInstance(Note1 note, NotesListPresenter presenter) {
        NoteItemFragmentUneditable fragment = new NoteItemFragmentUneditable();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE_2, note);
        args.putParcelable(ARG_PRESENTER_2, presenter);
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

        Note1 note = getArguments().getParcelable(ARG_NOTE_2);

        AppCompatImageView editButton = view.findViewById(R.id.edit_note_button_1);
        MaterialTextView noteNameView = view.findViewById(R.id.note_name_view);
        noteNameView.setText(note.getNoteName1());
        MaterialTextView noteDescriptionView = view.findViewById(R.id.note_description_view);
        noteDescriptionView.setText(note.getNoteDescription1());
        dateTextView = view.findViewById(R.id.note_date_created_view);
        displayDate();

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE_2, note);
                bundle.putBoolean(FRAGMENT_TYPE, true);

                getParentFragmentManager()
                        .setFragmentResult(KEY_NOTE_ITEM, bundle);
            }

        });
    }

    public void displayDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT);
        String date = dateFormat.format(calendar.getTime());
        dateTextView.setText(date);
    }

}