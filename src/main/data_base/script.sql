
-- Create table for users
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

-- Tasks table to store task details
CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   description TEXT,
   status VARCHAR(50) NOT NULL CHECK (status IN ('pending', 'in_progress', 'completed', 'overdue')),  -- Added 'overdue' status
   deadline TIMESTAMP NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   user_id INT NOT NULL,  -- Foreign key to reference the user responsible for the task
   manager_id INT,  -- Foreign key to reference the manager assigning the task
   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Tags table for managing task tags (many-to-many relationship)
CREATE TABLE tags (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

-- Associative table to link tasks with tags
CREATE TABLE task_tags (
   task_id INT NOT NULL,
   tag_id INT NOT NULL,
   PRIMARY KEY (task_id, tag_id),
   FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
   FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- Tokens table to manage user task modification and deletion tokens
CREATE TABLE tokens (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    task_id INT NOT NULL,
    modify_token_count INT DEFAULT 2,  -- 2 tokens per day for task modification
    delete_token_count INT DEFAULT 1,  -- 1 delete token per month
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);
