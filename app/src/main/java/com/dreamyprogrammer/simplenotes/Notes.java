package com.dreamyprogrammer.simplenotes;

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {
    private String note;
    private Boolean completed;

    public Notes(String note, Boolean completed) {
        this.note = note;
        this.completed = completed;
    }


    protected Notes(Parcel in) {
        note = in.readString();
        byte tmpCompleted = in.readByte();
        completed = tmpCompleted == 0 ? null : tmpCompleted == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(note);
        dest.writeByte((byte) (completed == null ? 0 : completed ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

}
