package com.MBAREK0.web.service;

import com.MBAREK0.web.entity.Task;
import com.MBAREK0.web.repository.TaskRepository;
import com.MBAREK0.web.repository.implementation.TaskRepositoryImpl;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(EntityManager entityManager) {
        this.taskRepository = new TaskRepositoryImpl(entityManager);
    }

    public Task createTask(Task task) {
        return taskRepository.createTask(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.getTaskById(id);
    }

    public Task updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    public Task deleteTask(Long id) {
        return taskRepository.deleteTask(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }


    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.getTasksByUserId(userId);
    }


}
