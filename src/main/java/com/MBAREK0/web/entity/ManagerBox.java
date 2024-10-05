package com.MBAREK0.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
@Getter
@Setter
@NoArgsConstructor
public class ManagerBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now(); // Direct initialization


    public ManagerBox(Task task, User user, String message) {
        this.task = task;
        this.user = user;
        this.message = message;
        // createdAt is already initialized directly
    }


    @Override
    public String toString() {
        return "ManagerBox{" +
                "id=" + id +
                ", task=" + task +
                ", user=" + user +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
