package com.example.notesapp.ui.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.notesapp.R;
import com.example.notesapp.domain.ExistingNotesRepository;
import com.example.notesapp.domain.Note1;
import com.example.notesapp.ui.noteItem.NoteItemFragment;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    private LinearLayout notesListRoot;
    private static NotesListPresenter presenter;
    public static final String KEY_NOTES_LIST = "KEY_NOTES_LIST";
    public static final String ARG_NOTES_LIST = "ARG_NOTES_LIST";
    //public static final String ARG_PRESENTER = "presenter";

    public NotesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //presenter = getArguments().getParcelable(ARG_PRESENTER);
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

        for (Note1 note : notesList) {
            // в лендскейпе заметка после сохранения не отображается в списке сразу
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.notes_list_item, notesListRoot, false);
            MaterialTextView itemNoteNameField = itemView.findViewById(R.id.notes_list_item_name_field);
            MaterialTextView itemNoteDateField = itemView.findViewById(R.id.notes_list_item_date_field);
            MaterialTextView itemNoteDescriptionField = itemView.findViewById(R.id.notes_list_item_description_field);

            itemView.setOnClickListener(v -> {

                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTES_LIST, note);

                getParentFragmentManager()
                        .setFragmentResult(KEY_NOTES_LIST, bundle);
            });

            itemNoteNameField.setText(note.getNoteName1());
            itemNoteDateField.setText(note.getDate1());
            itemNoteDescriptionField.setText(note.getNoteDescription1());

            notesListRoot.addView(itemView);
        }

    }

    public static NotesListPresenter getPresenter() {
        return presenter;
    }
}