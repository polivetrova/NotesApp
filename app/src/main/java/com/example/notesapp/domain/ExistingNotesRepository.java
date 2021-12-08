package com.example.notesapp.domain;

import java.util.ArrayList;
import java.util.List;

public class ExistingNotesRepository implements NotesRepository {

    ArrayList<Note1> result = new ArrayList<>();

    @Override
    public List<Note1> getNotes() {
        result.add(new Note1("note 1", "12.1.2000", "NOthing"));
        result.add(new Note1("note 1", "12.1.2000", "NOthing"));
        result.add(new Note1("note 1", "12.1.2000", "NOthing"));
        return result;
    }

    @Override
    public void addNoteToRepository(String noteName, String date, String noteDescription) {
        result.add(new Note1(noteName, date, noteDescription));
    }

//    @Override
//    public void deleteNoteFromRepository() {
//        // как-то передавать сюда индекс (порядковый номер) заметки и по нему уже удалять???
//    }
}