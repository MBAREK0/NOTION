package com.MBAREK0.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**

 CREATE TABLE users (
 id SERIAL PRIMARY KEY,
 username VARCHAR(50) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 first_name VARCHAR(100) NOT NULL,
 last_name VARCHAR(100) NOT NULL,
 email VARCHAR(100) NOT NULL UNIQUE,
 role VARCHAR(50) NOT NULL CHECK (role IN ('user', 'manager')),  -- Check applied to 'role'
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );
 */

// ALTER TABLE users ADD COLUMN eligible_for_double_tokens BOOLEAN DEFAULT FALSE;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @OneToMany(mappedBy = "manager")
    private List<Task> managedTasks;

    @OneToOne(mappedBy = "user")
    private Token token;

    @Column(name = "eligible_for_double_tokens", nullable = false)
    private int eligibleForDoubleTokens;

    @OneToMany(mappedBy = "user")
    private List<TaskModificationRequest> taskModificationRequests;

    @OneToMany(mappedBy = "manager")
    private List<TaskModificationRequest> managedTaskModificationRequests;


    public User(String username, String password, String firstName, String lastName, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String username, String password, String firstName, String lastName, String email, UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



}

