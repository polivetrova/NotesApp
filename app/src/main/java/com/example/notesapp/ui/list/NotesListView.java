package com.example.notesapp.ui.list;

import com.example.notesapp.domain.Note1;

public interface NotesListView {

    void createFragmentResultBundle(Note1 note, boolean isEditable);
}