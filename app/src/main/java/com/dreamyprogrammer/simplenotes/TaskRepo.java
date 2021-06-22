package com.dreamyprogrammer.simplenotes;

import java.util.List;

public interface TaskRepo {
    List<TaskElement> getTasks();

    void deleteTask(int id);

    void updateTask(TaskElement task);

    void createTask(TaskElement task);

}
