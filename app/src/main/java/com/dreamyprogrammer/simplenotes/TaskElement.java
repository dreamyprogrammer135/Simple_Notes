package com.dreamyprogrammer.simplenotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class TaskElement implements Parcelable {


    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    private String id;
    private String title;
    private List<Notes> notes = new ArrayList<>();
    private Long createDate;
    private Integer typeElement;
    private String groupPath;
    // todo 03.06.2021  подумать с приоритетом возможно и не надо
    private Integer priority;
    private Long timeReminder;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }

    private Integer delete;


    public TaskElement() {

    }

    public TaskElement(String title, List<Notes> notes, Integer typeElement, String groupPath) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.notes = notes;
        this.createDate = Calendar.getInstance().getTimeInMillis();
        this.typeElement = typeElement;
        //todo пока иерархии нет будем хранить id
        if (!groupPath.equals("")) {
            this.groupPath = groupPath;
        } else {
            this.groupPath = this.id;
        }
    }

    protected TaskElement(Parcel in) {
        id = in.readString();
        title = in.readString();
        notes = in.createTypedArrayList(Notes.CREATOR);
        if (in.readByte() == 0) {
            createDate = null;
        } else {
            createDate = in.readLong();
        }
        if (in.readByte() == 0) {
            typeElement = null;
        } else {
            typeElement = in.readInt();
        }
        groupPath = in.readString();
        if (in.readByte() == 0) {
            priority = null;
        } else {
            priority = in.readInt();
        }
        if (in.readByte() == 0) {
            timeReminder = null;
        } else {
            timeReminder = in.readLong();
        }
        if (in.readByte() == 0) {
            delete = null;
        } else {
            delete = in.readInt();
        }
    }

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

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Notes> getNotes() {
        return notes;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public Integer getTypeElement() {
        return typeElement;
    }

    public String getGroupPath() {
        return groupPath;
    }

    public Integer getPriority() {
        return priority;
    }

    public Long getTimeReminder() {
        return timeReminder;
    }

    public Integer getDelete() {
        return delete;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeTypedList(notes);
        if (createDate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(createDate);
        }
        if (typeElement == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(typeElement);
        }
        dest.writeString(groupPath);
        if (priority == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(priority);
        }
        if (timeReminder == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(timeReminder);
        }
        if (delete == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(delete);
        }
    }
}
