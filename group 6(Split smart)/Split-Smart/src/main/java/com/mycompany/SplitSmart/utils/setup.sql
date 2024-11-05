-- Create Database
CREATE DATABASE IF NOT EXISTS splitsmart;

-- Use the created database
USE splitsmart;

-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create Expense Table
CREATE TABLE expenses (
    expense_id VARCHAR(36) PRIMARY KEY,
    category VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    from_user VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE expense_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    expense_id VARCHAR(36) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    share DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (expense_id) REFERENCES expenses(expense_id) ON DELETE CASCADE
);

-- Optionally, insert some initial data (like an admin user)
INSERT INTO users (username, password) VALUES ('admin', '123');