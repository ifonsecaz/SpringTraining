package com.task.management.service;


import java.util.List;

import com.task.management.entity.Task;


public interface TaskService {
    //Save
    public Task saveTask(Task task);

    //Read  
    public List<Task> fetchTaskList();

    //Read 1
    public Task fetchTaskByID(Long taskID);

    //Delete
    public boolean deleteTask(Long taskID);

    //Update
    public Task updateTask(Task task, Long taskID);
}

