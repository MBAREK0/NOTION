package com.MBAREK0.web.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * CREATE TABLE tasks (
 *    id SERIAL PRIMARY KEY,
 *    title VARCHAR(255) NOT NULL,
 *    description TEXT,
 *    status VARCHAR(50) NOT NULL CHECK (status IN ('pending', 'in_progress', 'completed', 'overdue')),  -- Added 'overdue' status
 *    deadline TIMESTAMP NOT NULL,
 *    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *    user_id INT NOT NULL,  -- Foreign key to reference the user responsible for the task
 *    manager_id INT,  -- Foreign key to reference the manager assigning the task
 *    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
 *    FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE SET NULL
 * );
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;


    public Task(String title, String description, TaskStatus status, LocalDateTime deadline, User user, User manager) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.user = user;
        this.manager = manager;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task(String title, String description, TaskStatus status, LocalDateTime deadline, User user, User manager, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.user = user;
        this.manager = manager;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
