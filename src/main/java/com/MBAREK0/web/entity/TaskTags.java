package com.MBAREK0.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -- Associative table to link tasks with tags
 * CREATE TABLE task_tags (
 * id SERIAL PRIMARY KEY,
 *    task_id INT NOT NULL,
 *    tag_id INT NOT NULL,
 *    PRIMARY KEY (task_id, tag_id),
 *    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
 *    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
 * );
 */
@Entity
@Table(name = "task_tags")
@Getter
@Setter
@NoArgsConstructor
public class TaskTags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public TaskTags(Task task, Tag tag) {
        this.task = task;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "TaskTags{" +
                "task=" + task +
                ", tag=" + tag +
                '}';
    }
}
