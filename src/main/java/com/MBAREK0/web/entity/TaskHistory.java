package com.MBAREK0.web.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * CREATE TABLE task_history (
 *     id SERIAL PRIMARY KEY,
 *     task_id INT NOT NULL,  -- Foreign key to reference the associated task
 *     change_type VARCHAR(20) NOT NULL CHECK (change_type IN ('modification', 'deletion')),  -- Change type: modification or deletion
 *     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp of the record creation
 *     status VARCHAR(20) NOT NULL CHECK (status IN ('accepted', 'pending')),  -- Status of the change
 *     FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE  -- Ensures that task history is deleted if the task is deleted
 * );
 */
@Entity
@Table(name = "task_history")
@Getter
@Setter
@NoArgsConstructor
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "change_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskHistoryStatus status;


    public TaskHistory(Task task, ChangeType changeType) {
        this.task = task;
        this.changeType = changeType;
        this.status = TaskHistoryStatus.pending;
        this.createdAt = LocalDateTime.now();
    }

    public TaskHistory(Task task, ChangeType changeType, TaskHistoryStatus status) {
        this.task = task;
        this.changeType = changeType;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "TaskHistory{" +
                "status=" + status +
                ", createdAt=" + createdAt +
                ", changeType=" + changeType +
                ", task=" + task +
                '}';
    }
}
