package com.example.notesapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Note1 implements Parcelable {

    private final String noteName1;
    private final String noteDescription1;
    private final String date1;

    public Note1(String noteName1, String date1, String noteDescription1) {
        this.noteName1 = noteName1;
        this.noteDescription1 = noteDescription1;
        this.date1 = date1;
    }

    protected Note1(Parcel in) {
        noteName1 = in.readString();
        noteDescription1 = in.readString();
        date1 = in.readString();
    }

    public static final Creator<Note1> CREATOR = new Creator<Note1>() {
        @Override
        public Note1 createFromParcel(Parcel in) {
            return new Note1(in);
        }

        @Override
        public Note1[] newArray(int size) {
            return new Note1[size];
        }
    };

    public String getNoteName1() {
        return noteName1;
    }

    public String getNoteDescription1() {
        return noteDescription1;
    }

    public String getDate1() {
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