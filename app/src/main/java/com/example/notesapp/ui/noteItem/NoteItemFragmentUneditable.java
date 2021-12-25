package com.example.notesapp.ui.noteItem;

import static com.example.notesapp.ui.list.NotesListFragment.FRAGMENT_TYPE;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.example.notesapp.R;
import com.example.notesapp.domain.Note;
import com.example.notesapp.ui.MainActivity;
import com.google.android.material.textview.MaterialTextView;

public class NoteItemFragmentUneditable extends Fragment {

    public static final String ARG_NOTE_2 = "note 2";
    public static final String KEY_NOTE_ITEM = "KEY_NOTE_ITEM";

    public NoteItemFragmentUneditable() {
        super(R.layout.fragment_note_item_uneditable);
    }

    public static NoteItemFragmentUneditable newInstance(Note note) {
        NoteItemFragmentUneditable fragment = new NoteItemFragmentUneditable();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE_2, note);
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

        Note note = getArguments().getParcelable(ARG_NOTE_2);

        AppCompatImageView editButton = view.findViewById(R.id.edit_note_button_1);
        MaterialTextView noteNameView = view.findViewById(R.id.note_name_view);
        noteNameView.setText(note.getNoteName());
        MaterialTextView noteDescriptionView = view.findViewById(R.id.note_description_view);
        noteDescriptionView.setText(note.getNoteDescription());
        MaterialTextView dateTextView = view.findViewById(R.id.note_date_created_view);
        MainActivity.displayDate(dateTextView);

        editButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ARG_NOTE_2, note);
            bundle.putBoolean(FRAGMENT_TYPE, true);

            getParentFragmentManager()
                    .setFragmentResult(KEY_NOTE_ITEM, bundle);
        });
    }
}
