package com.example.notesapp.ui.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.notesapp.R;
import com.example.notesapp.domain.ExistingNotesRepository;
import com.example.notesapp.domain.Note1;
import com.example.notesapp.ui.MainActivity;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    private LinearLayout notesListRoot;
    public static NotesListPresenter presenter;
    public static final String KEY_NOTES_LIST = "KEY_NOTES_LIST";
    public static final String ARG_NOTES_LIST = "ARG_NOTES_LIST";
    public static final String FRAGMENT_TYPE = "NoteItemFragmentEditable or Uneditable";

    public NotesListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotesListPresenter(this, new ExistingNotesRepository());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notesListRoot = view.findViewById(R.id.notes_root);
        presenter.requestNotes();
    }

    @Override
    public void showNotesList(List<Note1> notesList) {

        ((MainActivity) getActivity()).showFloatingActionButton();

        for (Note1 note : notesList) {
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.notes_list_item, notesListRoot, false);
            MaterialTextView itemNoteNameField = itemView.findViewById(R.id.notes_list_item_name_field);
            MaterialTextView itemNoteDateField = itemView.findViewById(R.id.notes_list_item_date_field);
            MaterialTextView itemNoteDescriptionField = itemView.findViewById(R.id.notes_list_item_description_field);
            AppCompatImageView openTheNoteButton = itemView.findViewById(R.id.open_note_button);
            AppCompatImageView editTheNoteButton = itemView.findViewById(R.id.edit_note_button);

            itemView.setOnClickListener(v -> {

                openTheNoteButton.setVisibility(View.VISIBLE);
                editTheNoteButton.setVisibility(View.VISIBLE);

                openTheNoteButton.setOnClickListener(v1 -> createFragmentResultBundle(note, false));
                editTheNoteButton.setOnClickListener(v2 -> createFragmentResultBundle(note, true));

                new Handler().postDelayed(() -> {
                    openTheNoteButton.setVisibility(View.INVISIBLE);
                    editTheNoteButton.setVisibility(View.INVISIBLE);
                }, 5000);

            });

            itemView.setOnLongClickListener(v -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Want to delete the note?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            presenter.deleteNote(note);
                            itemView.setVisibility(View.GONE);
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
                return false;
            });

            itemNoteNameField.setText(note.getNoteName1());
            itemNoteDateField.setText(note.getDate1());
            itemNoteDescriptionField.setText(note.getNoteDescription1());

            notesListRoot.addView(itemView);
        }
    }

    public void createFragmentResultBundle(Note1 note, boolean isEditable) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTES_LIST, note);
        bundle.putBoolean(FRAGMENT_TYPE, isEditable);

        getParentFragmentManager()
                .setFragmentResult(KEY_NOTES_LIST, bundle);
    }
}