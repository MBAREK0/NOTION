package com.MBAREK0.web.service;

import com.MBAREK0.web.entity.Task;
import com.MBAREK0.web.entity.TaskModificationRequest;
import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.repository.TaskRepository;
import com.MBAREK0.web.repository.implementation.TaskRepositoryImpl;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(EntityManager entityManager) {
        this.taskRepository = new TaskRepositoryImpl(entityManager);
    }

    public Task createTask(Task task) {
        return taskRepository.createTask(task);
    }

    public Optional<Task> getTaskById(Long id) {
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

    public List<Task> getAllTasksByUserId(Long userId) {
        return taskRepository.getTasksByUserId(userId);
    }

    public List<Task> getAllTasksByManagerId(Long userId) {
        return taskRepository.getTasksByManagerId(userId);
    }

    public TaskModificationRequest requestTaskModification(Task task, User user, User manager) {
        if (task == null || user == null || manager == null) {
            throw new IllegalArgumentException("Task, user and manager must be provided");
        }

        if (task.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("Only the user assigned to the task can request a modification");
        }
        TaskModificationRequest request = new TaskModificationRequest(task, user, manager);
        return taskRepository.requestTaskModification(request);


    }

    public List<TaskModificationRequest> getAllTaskModificationRequestsByManagerId(Long managerId) {
        return taskRepository.getAllTaskModificationRequestsByManagerId(managerId);
    }

    public Optional<TaskModificationRequest> getTaskModificationRequestById(Long id) {
        return taskRepository.getTaskModificationRequestById(id);
    }



    public TaskModificationRequest updateTaskModificationRequest(TaskModificationRequest request) {
        return taskRepository.updateTaskModificationRequest(request);
    }

    public List<TaskModificationRequest> getPendingRequestsOlderThan12Hours() {
        return taskRepository.getPendingRequestsOlderThan12Hours();
    }
}

