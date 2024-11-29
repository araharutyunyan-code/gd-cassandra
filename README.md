# GD - Cassandra Q/A

# Setup Environment

1. Install Apache Cassandra on your local machine or use a cloud service.
2. Set up your Java development environment (IDE like IntelliJ IDEA or Eclipse).
3. Include necessary libraries for Cassandra (e.g., DataStax Java Driver).

---

# Design the Database Schema

1. Create a keyspace for your task management application.
2. Design the following tables:
    - **Users**: (user_id, username, email)
    - **Tasks**: (task_id, user_id, title, description, status, created_at)

---

# Implement CRUD Operations

## Create:
- Function to add a new user.
- Function to create a new task associated with a user.

## Read:
- Function to retrieve all tasks for a specific user by their user ID.
- Function to get details of a specific task by its ID.

## Update:
- Function to update the status of a task (e.g., from “in progress” to “completed”).

## Delete:
- Function to delete a task by its ID.

## Docker Compose && Command to run cqlsh
#### docker exec -it cassandra cqlsh -u cassandra -p cassandra
