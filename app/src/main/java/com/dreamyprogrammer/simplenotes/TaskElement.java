package com.dreamyprogrammer.simplenotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class TaskElement implements Parcelable {
    public static final Creator<TaskElement> CREATOR = new Creator<TaskElement>() {
        @Override
        public TaskElement createFromParcel(Parcel in) {
            return new TaskElement(in);
        }

        @Override
        public TaskElement[] newArray(int size) {
            return new TaskElement[size];
        }
    };
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    private String id;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }



    private String title;
    private Long createDate;
    // todo 03.06.2021  подумать с приоритетом возможно и не надо
    private Integer priority;
    private Integer typeElement;
    //todo 03.06.2021 хочу заметки с иерархией (папочки будут)
    private String idGroupElement;
    private Long timeReminder;
    private Integer delete;
    //todo 07.06.2021 поле только для 6-го урока. Далее удалим за ненадобностью.
    private String notes;

    protected TaskElement(Parcel in) {
        id = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            priority = null;
        } else {
            priority = in.readInt();
        }
        if (in.readByte() == 0) {
            typeElement = null;
        } else {
            typeElement = in.readInt();
        }
        idGroupElement = in.readString();
        if (in.readByte() == 0) {
            delete = null;
        } else {
            delete = in.readInt();
        }
        notes = in.readString();
    }

    public TaskElement(String name, Integer typeElement) {
        this.title = name;
        this.typeElement = typeElement;
        this.createDate = getCurrentDate();
        this.id = UUID.randomUUID().toString();
    }

    public static long getCurrentDate() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public String getId() {
        return id;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public String getIdGroupElement() {
        return idGroupElement;
    }

    public Integer getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(Integer typeElement) {
        this.typeElement = typeElement;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        if (priority == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(priority);
        }
        if (typeElement == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(typeElement);
        }
        dest.writeString(idGroupElement);
        if (delete == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(delete);
        }
        dest.writeString(notes);
    }
}
