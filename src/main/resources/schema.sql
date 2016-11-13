
-- SEQUENCES
CREATE SEQUENCE IF NOT EXISTS seq_user;
CREATE SEQUENCE IF NOT EXISTS seq_role;
CREATE SEQUENCE IF NOT EXISTS seq_task;

-- TABLES
CREATE TABLE IF NOT EXISTS tab_user (
	id INT NOT NULL PRIMARY KEY,
	name NVARCHAR NOT NULL UNIQUE,
	encoded_password NVARCHAR NOT NULL,
	created_on TIMESTAMP DEFAULT current_timestamp() NOT NULL,
	updated_on TIMESTAMP DEFAULT current_timestamp() NOT NULL
);

CREATE TABLE IF NOT EXISTS tab_role (
	id INT NOT NULL PRIMARY KEY,
	name NVARCHAR NOT NULL UNIQUE,
	created_on TIMESTAMP DEFAULT current_timestamp() NOT NULL,
	updated_on TIMESTAMP DEFAULT current_timestamp() NOT NULL
);

CREATE TABLE IF NOT EXISTS tab_user_role (
	user_id INT NOT NULL,
	role_id INT NOT NULL,
	created_on TIMESTAMP DEFAULT current_timestamp() NOT NULL,
	updated_on TIMESTAMP DEFAULT current_timestamp() NOT NULL,
	PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS tab_task (
	id INT NOT NULL PRIMARY KEY,
	assigned_to INT NOT NULL,
	title NVARCHAR NOT NULL,
	description NVARCHAR,
	priority INT DEFAULT 4 NOT NULL,
	finished BOOLEAN DEFAULT false NOT NULL,
	created_on TIMESTAMP DEFAULT current_timestamp() NOT NULL,
	updated_on TIMESTAMP DEFAULT current_timestamp() NOT NULL,
	finished_on TIMESTAMP
);

