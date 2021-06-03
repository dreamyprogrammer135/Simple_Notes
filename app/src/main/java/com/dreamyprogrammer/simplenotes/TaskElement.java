package com.dreamyprogrammer.simplenotes;

import java.util.Date;

public class TaskElement {
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

    public String getId() {
        return id;
    }

    public TaskElement(String name, Integer typeElement) {
        this.name = name;
        this.typeElement = typeElement;
        this.createDate = new Date();
        this.id = String.valueOf(this.name.hashCode())+String.valueOf(this.createDate.hashCode());
    }

    public Date getTimeReminder() {
        return timeReminder;
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

}
