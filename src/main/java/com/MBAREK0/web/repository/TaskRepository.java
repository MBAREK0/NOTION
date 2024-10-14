package com.MBAREK0.web.repository;

import com.MBAREK0.web.entity.Task;
import com.MBAREK0.web.entity.TaskModificationRequest;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task createTask(Task task);
    Optional<Task> getTaskById(Long id);
    List<Task> getTasksByUserId(Long userId);

    List<Task> getTasksByManagerId(Long userId);

    List<Task> getAllTasks();
    Task updateTask(Task task);
    Task deleteTask(Long id);
    TaskModificationRequest requestTaskModification(TaskModificationRequest request);

    List<TaskModificationRequest> getAllTaskModificationRequestsByManagerId(Long managerId);

    Optional<TaskModificationRequest> getTaskModificationRequestById(Long id);

    List<TaskModificationRequest> getPendingRequestsOlderThan12Hours();

    TaskModificationRequest updateTaskModificationRequest(TaskModificationRequest request);
}
