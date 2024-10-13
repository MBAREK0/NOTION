
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
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT NOT NULL,  -- Foreign key to reference the user responsible for the task
    manager_id INT,  -- Foreign key to reference the manager assigning the task
    ischanged BOOLEAN DEFAULT FALSE,  -- Added column to track task modification
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
   id SERIAL PRIMARY KEY,  -- New ID column
   task_id INT NOT NULL,
   tag_id INT NOT NULL,
   FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
   FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
   UNIQUE (task_id, tag_id)  -- Ensure task_id and tag_id combination is unique
);

-- Tokens table to manage user task modification and deletion tokens
CREATE TABLE tokens (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    modify_token_count INT DEFAULT 2,  -- 2 tokens per day for task modification
    delete_token_count INT DEFAULT 1,  -- 1 delete token per month
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
);


CREATE TABLE task_history (
    id SERIAL PRIMARY KEY,
    task_id INT NOT NULL,  -- Foreign key to reference the associated task
    inbox_id INT NOT NULL,  -- Foreign key to reference the manager inbox
    change_type VARCHAR(20) NOT NULL CHECK (change_type IN ('modification', 'deletion')),  -- Change type: modification or deletion
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp of the record creation
    status VARCHAR(20) NOT NULL CHECK (status IN ('accepted', 'pending')),  -- Status of the change
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE , -- Ensures that task history is deleted if the task is deleted
    FOREIGN KEY (inbox_id) REFERENCES manager_box(id) ON DELETE CASCADE
);

CREATE TABLE manager_box (
    id SERIAL PRIMARY KEY,
    task_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

/**

  -- track the task history
    -- create schedule function run every day to check the task status
        -- if task is overdue then update the status in task table
    -- create schedule function run every month to check the delete token
        -- update the delete token in token table to 1
    -- when delete the task should add the record in task_history table
 */

----------------------------------------------------------------------------------
-- Create a function to reset the modify_token_count to 2 for all users every day

CREATE OR REPLACE FUNCTION reset_modify_tokens()
RETURNS void AS $$
BEGIN
    UPDATE tokens
    SET modify_token_count = 2,
        updated_at = CURRENT_TIMESTAMP;
END;
$$ LANGUAGE plpgsql;

-- Create a job to run the function every day at midnight
SELECT cron.schedule(
               'reset_modify_tokens to 2',  -- Job name
               '0 0 * * *',                 -- Cron expression: At 00:00 every day
       $$SELECT reset_modify_tokens();$$    -- Job command
);

----------------------------------------------------------------------------------
 -- Create a function to reset the delete_token_count to 1 for all users every month
CREATE OR REPLACE FUNCTION reset_delete_tokens()
RETURNS void AS $$
BEGIN
    UPDATE tokens
    SET delete_token_count = 1,
        updated_at = CURRENT_TIMESTAMP;
END;
$$ LANGUAGE plpgsql;

-- Create a job to run the function every month on the 1st day at midnight
SELECT cron.schedule(
       'Monthly reset of delete tokens to 1',  -- Job name
       '0 0 1 * *',                            -- Cron expression: At 00:00 every day
       $$SELECT reset_delete_tokens();$$       -- Job command
);

----------------------------------------------------------------------------------
-- Create a function to mark incomplete tasks as overdue
CREATE OR REPLACE FUNCTION mark_incomplete_tasks()
RETURNS void AS $$
BEGIN
    UPDATE tasks
    SET status = 'overdue'
    WHERE status != 'completed' AND end_date < CURRENT_TIMESTAMP;
END;
$$ LANGUAGE plpgsql;

-- Create a job to run the function every day at midnight
SELECT cron.schedule(
               'Mark tasks as overdue every 24 hours',  -- Job name
               '0 0 * * *',                             -- Cron expression: At 00:30 every day
               $$SELECT mark_incomplete_tasks();$$      -- SQL command to run
);


----------------------------------------------------------------------------------
-- commands
 -- This command will show all the jobs scheduled in the database
SELECT * FROM cron.job ORDER BY jobid;
 -- This command will unschedule the job with jobid 2
SELECT cron.unschedule(2);