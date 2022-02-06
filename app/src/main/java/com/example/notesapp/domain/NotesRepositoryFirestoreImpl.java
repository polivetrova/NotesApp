package com.example.notesapp.domain;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class NotesRepositoryFirestoreImpl implements NotesRepository {

    private static final String NOTES_COLLECTION = "notes";
    private static final String TAG = "[NotesRepositoryFirestoreImpl]";

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference collection = firestore.collection(NOTES_COLLECTION);
    private ArrayList<Note> notes = new ArrayList<>();

    @Override
    public NotesRepository init(NotesRepositoryResponse notesRepositoryResponse) {
        collection.orderBy((NotesMapping.Fields.NOTE_DATE), Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                notes = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    Map<String, Object> doc = documentSnapshot.getData();
                    String id = documentSnapshot.getId();
                    Note note = NotesMapping.toNote(id, doc);
                    notes.add(note);
                }
                    Log.d(TAG, "success " + notes.size() + " qnt");
                notesRepositoryResponse.initialized(NotesRepositoryFirestoreImpl.this);
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        }).addOnFailureListener(e -> Log.d(TAG, "get failed with ", e));
        return this;
    }

    @Override
    public ArrayList<Note> getNotes() {
        return notes;
    }

    @Override
    public void addNoteToRepository(String noteName, String date, String noteDescription) {
        Note note = new Note(noteName, date, noteDescription);
        collection.add(NotesMapping.toDocument(note))
                .addOnSuccessListener(documentReference -> note.setId(documentReference.getId()));
        notes.add(note);
    }

    @Override
    public void deleteNoteFromRepository(Note note) {
        collection.document(note.getId()).delete();
        notes.remove(note);
    }

    @Override
    public void rewriteNote(Note note, String noteName, String date, String noteDescription) {
        String id = note.getId();
        collection.document(id).set(NotesMapping.toDocument(note));
        notes.set(notes.indexOf(note), new Note(noteName, date, noteDescription));
    }
}
