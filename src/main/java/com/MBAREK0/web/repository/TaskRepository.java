package com.MBAREK0.web.repository;

import com.MBAREK0.web.entity.Task;

import java.util.List;

public interface TaskRepository {
    Task createTask(Task task);
    Task getTaskById(Long id);
    List<Task> getTasksByUserId(Long userId);
    List<Task> getAllTasks();
    Task updateTask(Task task);
    Task deleteTask(Long id);
}
