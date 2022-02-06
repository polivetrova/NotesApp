package com.example.notesapp.ui.list;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.notesapp.domain.Note;
import com.example.notesapp.domain.NotesRepository;
import com.example.notesapp.domain.NotesRepositoryFirestoreImpl;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class NotesListPresenter implements Parcelable {

    private NotesListView view;
    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    protected NotesListPresenter(Parcel in) {
    }

    public static final Creator<NotesListPresenter> CREATOR = new Creator<NotesListPresenter>() {
        @Override
        public NotesListPresenter createFromParcel(Parcel in) {
            return new NotesListPresenter(in);
        }

        @Override
        public NotesListPresenter[] newArray(int size) {
            return new NotesListPresenter[size];
        }
    };

    public ArrayList<Note> requestNotes() {
        return repository.getNotes();
    }

    public void openNote(Note note, boolean isEditable) {
        view.createFragmentResultBundle(note, isEditable);
    }

    public void saveNote(String noteName, String date, String noteDescription) {
        repository.addNoteToRepository(noteName, date, noteDescription);
        updateDataInSharedPref();
    }

    public void rewriteNote(Note note, String noteName, String date, String noteDescription) {
        repository.rewriteNote(note, noteName, date, noteDescription);
        updateDataInSharedPref();
    }

    public void deleteNote(Note note) {
        repository.deleteNoteFromRepository(note);
        updateDataInSharedPref();
    }

    private void updateDataInSharedPref() {
        String jsonNotes = new GsonBuilder().create().toJson(repository.getNotes());
        view.putToSharedPref(jsonNotes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public NotesRepository initRepository(NotesListAdapter adapter) {
        new NotesRepositoryFirestoreImpl().init(notesData -> adapter.notifyDataSetChanged());
        return repository;
    }
}