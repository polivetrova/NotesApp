package com.example.notesapp.domain;

import java.util.List;

public interface NotesRepository {

    List<Note> getNotes();

    void addNoteToRepository(String noteName, String date, String noteDescription);
    void deleteNoteFromRepository(Note note);
}