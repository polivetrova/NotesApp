package com.example.notesapp.domain;

import java.util.HashMap;
import java.util.Map;

public class NotesMapping {

    public static Note toNote(String id, Map<String, Object> doc) {
        String name = (String) doc.get(Fields.NOTE_NAME);
        String date = (String) doc.get(Fields.NOTE_DATE);
        String description = (String) doc.get(Fields.NOTE_DESCRIPTION);

        Note result = new Note(name, date, description);
        result.setId(id);
        return result;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> result = new HashMap<>();
        result.put(Fields.NOTE_NAME, note.getNoteName());
        result.put(Fields.NOTE_DATE, note.getDate());
        result.put(Fields.NOTE_DESCRIPTION, note.getNoteDescription());

        return result;
    }

    public static class Fields {
        public final static String NOTE_NAME = "name";
        public final static String NOTE_DATE = "date";
        public final static String NOTE_DESCRIPTION = "description";
    }
}
