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
 *     modify_token_count INT DEFAULT 2,  -- 2 tokens per day for task modification
 *     delete_token_count INT DEFAULT 1,  -- 1 delete token per month
 *     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
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

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "modify_token_count", nullable = false)
    private int modifyTokenCount;

    @Column(name = "delete_token_count", nullable = false)
    private int deleteTokenCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Token(User user, int modifyTokenCount, int deleteTokenCount) {
        this.user = user;
        this.modifyTokenCount = modifyTokenCount;
        this.deleteTokenCount = deleteTokenCount;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Token(User user) {
        this.user = user;
        this.modifyTokenCount = 2;
        this.deleteTokenCount = 1;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Token(User user, int modifyTokenCount, int deleteTokenCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.modifyTokenCount = modifyTokenCount;
        this.deleteTokenCount = deleteTokenCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
