package com.MBAREK0.web.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * -- Tags table for managing task tags (many-to-many relationship)
 * CREATE TABLE tags (
 *   id SERIAL PRIMARY KEY,
 *   name VARCHAR(50) UNIQUE NOT NULL
 * );
 */

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Task> tasks = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        boolean added = tasks.add(task);
        if (added) {
            task.getTags().add(this);
        }
    }

    public void removeTask(Task task) {
        boolean removed = tasks.remove(task);
        if (removed) {
            task.getTags().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
