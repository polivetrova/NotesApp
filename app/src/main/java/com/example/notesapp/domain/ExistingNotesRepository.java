package com.example.notesapp.domain;

import java.util.ArrayList;
import java.util.List;

public class ExistingNotesRepository implements NotesRepository {

    ArrayList<Note> result;

    public ExistingNotesRepository() {
        result = new ArrayList<>();
    }

    @Override
    public List<Note> getNotes() {
        return result;
    }

    @Override
    public void addNoteToRepository(String noteName, String date, String noteDescription) {
        result.add(new Note(noteName, date, noteDescription));
    }

    @Override
    public void deleteNoteFromRepository(Note note) {
        result.remove(note);
    }

    @Override
    public void rewriteNote(Note note, String noteName, String date, String noteDescription) {
        result.set(result.indexOf(note), new Note(noteName, date, noteDescription));
    }
}