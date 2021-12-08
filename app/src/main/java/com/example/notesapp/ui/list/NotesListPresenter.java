package com.example.notesapp.ui.list;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.notesapp.domain.NotesRepository;

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

    public void saveNote(String noteName, String date, String noteDescription) {
        repository.addNoteToRepository(noteName, date, noteDescription);
    }

    public void requestNotes() {
        view.showNotesList(repository.getNotes());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}