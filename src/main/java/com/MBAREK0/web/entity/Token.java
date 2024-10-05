package com.MBAREK0.web.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * -- Tokens table to manage user task modification and deletion tokens
 * CREATE TABLE tokens (
 *     id SERIAL PRIMARY KEY,
 *     user_id INT NOT NULL,
 *     task_id INT NOT NULL,
 *     modify_token_count INT DEFAULT 2,  -- 2 tokens per day for task modification
 *     delete_token_count INT DEFAULT 1,  -- 1 delete token per month
 *     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
 *     FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
 * );
 */

@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "modify_token_count", nullable = false)
    private int modifyTokenCount;

    @Column(name = "delete_token_count", nullable = false)
    private int deleteTokenCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Token(User user, Task task, int modifyTokenCount, int deleteTokenCount) {
        this.user = user;
        this.task = task;
        this.modifyTokenCount = modifyTokenCount;
        this.deleteTokenCount = deleteTokenCount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Token(User user, Task task) {
        this(user, task, 2, 1);
    }

    public Token(User user, Task task, int modifyTokenCount, int deleteTokenCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.task = task;
        this.modifyTokenCount = modifyTokenCount;
        this.deleteTokenCount = deleteTokenCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Token{" +
                "updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", deleteTokenCount=" + deleteTokenCount +
                ", modifyTokenCount=" + modifyTokenCount +
                ", task=" + task +
                ", user=" + user +
                '}';
    }
}
