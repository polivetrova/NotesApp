package com.example.notesapp.ui.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
    private NotesListPresenter presenter;

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

        if (!notesList.isEmpty()) {

            for (Note1 note : notesList) { // после возвращения по кнопке "назад" все заметки не пересоздаются, а добавляются к тем, которые были созданы изначально - было три, после возвращения стало шесть - понять почему
                // в лендскейпе заметка после сохранения не отображается в списке сразу
                View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.notes_list_item, notesListRoot, false);
                MaterialTextView itemNoteNameField = itemView.findViewById(R.id.notes_list_item_name_field);
                MaterialTextView itemNoteDateField = itemView.findViewById(R.id.notes_list_item_date_field);
                MaterialTextView itemNoteDescriptionField = itemView.findViewById(R.id.notes_list_item_description_field);

                itemView.setOnClickListener(v -> {
                    NoteItemFragment noteItemFragment = NoteItemFragment.newInstance(note, presenter);
                    FragmentManager manager = getParentFragmentManager();
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

                itemNoteNameField.setText(note.getNoteName1());
                itemNoteDateField.setText(note.getDate1());
                itemNoteDescriptionField.setText(note.getNoteDescription1());

                notesListRoot.addView(itemView);
            }

        }
    }

    public NotesListPresenter getPresenter() {
        return presenter;
    }
}