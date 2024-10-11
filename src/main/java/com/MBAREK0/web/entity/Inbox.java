package com.MBAREK0.web.entity;


import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * CREATE TABLE manager_box (
 *     id SERIAL PRIMARY KEY,
 *     task_id INT NOT NULL,
 *     user_id INT NOT NULL,
 *     message TEXT NOT NULL,
 *     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
 *     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
 * );
 */
@Entity
@Table(name = "manager_box")
@NoArgsConstructor
@Getter
@Setter
public class Inbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    public Inbox(Task task, User user, String message) {
        this.task = task;
        this.user = user;
        this.message = message;
        this.createdAt = LocalDate.now();
    }


}