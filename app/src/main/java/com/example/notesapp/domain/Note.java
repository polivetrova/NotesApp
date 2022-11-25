package com.example.notesapp.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Note implements Parcelable {

    private String id;

    private final String noteName;
    private final String noteDescription;
    private final String date;

    public Note(String noteName, String date, String noteDescription) {
        this.id = "b" + Math.random();
        this.noteName = noteName;
        this.noteDescription = noteDescription;
        this.date = date;
        Log.i("id", id);
    }

    protected Note(Parcel in) {
        noteName = in.readString();
        noteDescription = in.readString();
        date = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getNoteName() {
        return noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteName);
        dest.writeString(noteDescription);
        dest.writeString(date);
    }
}