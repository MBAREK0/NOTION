package com.MBAREK0.web.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 CREATE TABLE tasks (
 id SERIAL PRIMARY KEY,
 title VARCHAR(255) NOT NULL,
 description TEXT,
 status VARCHAR(50) NOT NULL CHECK (status IN ('pending', 'in_progress', 'completed', 'overdue')),  -- Added 'overdue' status
 star_date TIMESTAMP NOT NULL,
 end_date TIMESTAMP NOT NULL,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 manager_id INT,  -- Foreign key to reference the manager assigning the task
 ischanged BOOLEAN DE// user_id INT NOT NULL,  -- Foreign key to reference the user responsible for the task
 FAULT FALSE,  -- Added column to track task modification
 FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
 FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE SET NULL
 );
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

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

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

    @Column(name = "ischanged" , columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isChanged;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"task_id", "tag_id"})
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "task")
    private List<Inbox> inbox;


    public Task(String title, String description, TaskStatus status, LocalDate start_date,LocalDate end_date, User user, User manager) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDate = start_date;
        this.endDate = end_date;
        this.user = user;
        this.manager = manager;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task(String title, String description, TaskStatus status,LocalDate start_date,LocalDate end_date, User user, User manager, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDate = start_date;
        this.endDate = end_date;
        this.user = user;
        this.manager = manager;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void addTag(Tag tag) {
        boolean added  = tags.add(tag);
        if (added)
        tag.getTasks().add(this);
    }

    public void removeTag(Tag tag) {
       boolean removed =  tags.remove(tag);
         if (removed)
        tag.getTasks().remove(this);
    }

}
