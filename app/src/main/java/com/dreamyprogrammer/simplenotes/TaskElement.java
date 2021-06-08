package com.dreamyprogrammer.simplenotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private String id;
    private String name;
    private Date createDate;
    // todo 03.06.2021  подумать с приоритетом возможно и не надо
    private Integer priority;
    private Integer typeElement;
    //todo 03.06.2021 хочу заметки с иерархией (папочки будут)
    private String idGroupElement;
    private Date timeReminder;
    private Integer delete;
    //todo 07.06.2021 поле только для 6-го урока. Далее удалим за ненадобностью.
    private String notes;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    protected TaskElement(Parcel in) {
        id = in.readString();
        name = in.readString();
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
        this.name = name;
        this.typeElement = typeElement;
        this.createDate = new Date();
        this.id = String.valueOf(this.name.hashCode()) + String.valueOf(this.createDate.hashCode());
    }

    public String getId() {
        return id;
    }

    public Date getTimeReminder() {
        return timeReminder;
    }

    public void setTimeReminder(Date timeReminder) {
        this.timeReminder = timeReminder;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        dest.writeString(name);
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
