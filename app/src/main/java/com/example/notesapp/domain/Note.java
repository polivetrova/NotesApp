package com.example.notesapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private final String noteName1;
    private final String noteDescription1;
    private final String date1;

    public Note(String noteName, String date, String noteDescription) {
        this.noteName1 = noteName;
        this.noteDescription1 = noteDescription;
        this.date1 = date;
    }

    protected Note(Parcel in) {
        noteName1 = in.readString();
        noteDescription1 = in.readString();
        date1 = in.readString();
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
        return noteName1;
    }

    public String getNoteDescription() {
        return noteDescription1;
    }

    public String getDate() {
        return date1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteName1);
        dest.writeString(noteDescription1);
        dest.writeString(date1);
    }
}