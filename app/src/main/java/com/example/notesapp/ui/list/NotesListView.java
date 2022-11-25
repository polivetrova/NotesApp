package com.example.notesapp.ui.list;

import com.example.notesapp.domain.Note;

public interface NotesListView {

    void createFragmentResultBundle(Note note, boolean isEditable);

    void putToSharedPref(String jsonNotes);
}