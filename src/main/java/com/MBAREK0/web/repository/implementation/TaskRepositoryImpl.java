package com.MBAREK0.web.repository.implementation;

import com.MBAREK0.web.entity.RequestStatus;
import com.MBAREK0.web.entity.Task;
import com.MBAREK0.web.entity.TaskModificationRequest;
import com.MBAREK0.web.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TaskRepositoryImpl implements TaskRepository {
    private EntityManager entityManager;

    public TaskRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Task createTask(Task task) {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();

        return task;
    }


    @Override
    public Optional<Task> getTaskById(Long id) {
        Task user =  entityManager.find(Task.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<Task> getTasksByUserId(Long userId) {
        return entityManager.createQuery("SELECT t FROM Task t WHERE t.user.id = :userId", Task.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Task> getTasksByManagerId(Long managerId) {
        TypedQuery<Task> query = entityManager.createQuery(
                "SELECT t FROM Task t WHERE t.manager.id = :managerId", Task.class);
        query.setParameter("managerId", managerId);
        return query.getResultList();
    }

    @Override
    public List<Task> getAllTasks() {
        return entityManager.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }


    @Override
    public Task updateTask(Task task) {
        entityManager.getTransaction().begin();
        entityManager.merge(task);
        entityManager.getTransaction().commit();

        return task;
    }

    @Override
    public Task deleteTask(Long id) {
        Task task = entityManager.find(Task.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(task);
        entityManager.getTransaction().commit();

        return task;
    }

    @Override
    public TaskModificationRequest requestTaskModification(TaskModificationRequest request) {
        entityManager.getTransaction().begin();
        entityManager.persist(request);
        entityManager.getTransaction().commit();

        return request;
    }

    @Override
    public List<TaskModificationRequest> getAllTaskModificationRequestsByManagerId(Long managerId) {
        return entityManager.createQuery("SELECT r FROM TaskModificationRequest r WHERE r.manager.id = :managerId and r.status = :pendingStatus", TaskModificationRequest.class)
                .setParameter("managerId", managerId)
                .setParameter("pendingStatus", RequestStatus.pending)
                .getResultList();
    }

    @Override
    public Optional<TaskModificationRequest> getTaskModificationRequestById(Long id) {
        TaskModificationRequest request = entityManager.find(TaskModificationRequest.class, id);
        return Optional.ofNullable(request);
    }


    @Override
    public List<TaskModificationRequest> getPendingRequestsOlderThan12Hours() {
        LocalDateTime twelveHoursAgo = LocalDateTime.now().minusHours(12);
        return entityManager.createQuery("SELECT r FROM TaskModificationRequest r WHERE r.managerResponse = false AND r.requestTime < :twelveHoursAgo AND r.status = :pendingStatus", TaskModificationRequest.class)
                .setParameter("twelveHoursAgo", twelveHoursAgo)
                .setParameter("pendingStatus", RequestStatus.pending)
                .getResultList();
    }

    @Override
    public TaskModificationRequest updateTaskModificationRequest(TaskModificationRequest request) {
        entityManager.getTransaction().begin();
        entityManager.merge(request);
        entityManager.getTransaction().commit();
        return request;
    }
}
