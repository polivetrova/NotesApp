package com.example.notesapp.domain;

import java.util.List;

public interface NotesRepository {

    List<Note1> getNotes();
    void addNoteToRepository(String noteName, String date, String noteDescription);
    //void deleteNoteFromRepository();
}