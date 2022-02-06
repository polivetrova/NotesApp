package com.example.notesapp.domain;

import java.util.ArrayList;

public interface NotesRepository {

    ArrayList<Note> getNotes();

    void addNoteToRepository(String noteName, String date, String noteDescription);

    void deleteNoteFromRepository(Note note);

    void rewriteNote(Note note, String noteName, String date, String noteDescription);
}