package com.MBAREK0.web.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CREATE TABLE task_modification_requests (
 *     id BIGINT PRIMARY KEY,
 *     task_id BIGINT,
 *     user_id BIGINT,
 *     manager_id BIGINT,
 *     request_time TIMESTAMP,
 *     manager_response BOOLEAN DEFAULT FALSE,
 *     response_time TIMESTAMP
 * );
 */

@Entity
@Table(name = "task_modification_requests")
@NoArgsConstructor
@Getter
@Setter
public class TaskModificationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @Column(name = "request_time", nullable = false)
    private LocalDateTime requestTime;

    @Column(name = "manager_response", nullable = false)
    private boolean managerResponse;

    @Column(name = "response_time")
    private LocalDateTime responseTime;

    public TaskModificationRequest(Task task, User user, User manager) {
        this.task = task;
        this.user = user;
        this.manager = manager;
        this.requestTime = LocalDateTime.now();
        this.managerResponse = false;
        this.status = RequestStatus.pending;
    }

}
