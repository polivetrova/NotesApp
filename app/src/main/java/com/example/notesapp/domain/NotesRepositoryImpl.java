package com.example.notesapp.domain;

import java.util.ArrayList;

public class NotesRepositoryImpl implements NotesRepository {

    private ArrayList<Note> result;

    public NotesRepositoryImpl() {
        result = new ArrayList<>();
    }

    @Override
    public NotesRepository init(NotesRepositoryResponse notesRepositoryResponse) {
        if (notesRepositoryResponse != null) {
            notesRepositoryResponse.initialized(this);
        }
        return this;
    }

    @Override
    public ArrayList<Note> getNotes() {
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