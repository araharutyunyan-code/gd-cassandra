package am.gd.app.repository;

import am.gd.app.model.Task;
import am.gd.app.model.TaskStatus;
import am.gd.app.model.User;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskManagementRepository {

    private final CqlSession session;

    private static final String USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
                user_id uuid PRIMARY KEY,
                username text,
                email text
            )
            """;

    private static final String TASKS_TABLE = """
            CREATE TABLE IF NOT EXISTS tasks (
                task_id uuid,
                user_id uuid,
                title text,
                description text,
                status text,
                created_at timestamp,
                PRIMARY KEY (user_id, task_id)
            )
            """;

    public TaskManagementRepository(CqlSession session) {
        this.session = session;
        initializeTables();
    }

    private void initializeTables() {
        session.execute(USERS_TABLE);
        session.execute(TASKS_TABLE);
    }

    public void addUser(User user) {
        var query = "INSERT INTO users (user_id, username, email) VALUES (?, ?, ?)";
        session.execute(query, user.userId(), user.username(), user.email());
    }

    public void createTask(Task task) {
        var query = "INSERT INTO tasks (task_id, user_id, title, description, status, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        session.execute(query,
                task.taskId(),
                task.userId(),
                task.title(),
                task.description(),
                task.status().name(),
                task.createdAt()
        );
    }

    public List<Task> getTasksByUserId(UUID userId) {
        var query = "SELECT * FROM tasks WHERE user_id = ?";
        var tasks = new ArrayList<Task>();

        for (var row : session.execute(query, userId)) {
            tasks.add(mapRowToTask(row));
            System.out.println(mapRowToTask(row));
        }

        return tasks;
    }

    public Optional<Task> getTaskById(UUID userId, UUID taskId) {
        var query = "SELECT * FROM tasks WHERE user_id = ? AND task_id = ?";
        var row = session.execute(query, userId, taskId).one();

        return Optional.ofNullable(row).map(this::mapRowToTask);
    }

    private Task mapRowToTask(Row row) {
        return new Task(
                row.getUuid("task_id"),
                row.getUuid("user_id"),
                row.getString("title"),
                row.getString("description"),
                TaskStatus.valueOf(row.getString("status")),
                row.getInstant("created_at")
        );
    }

    public void updateTaskStatus(UUID userId, UUID taskId, TaskStatus newStatus) {
        var query = "UPDATE tasks SET status = ? WHERE user_id = ? AND task_id = ?";
        session.execute(query, newStatus.name(), userId, taskId);
    }

    public void deleteTask(UUID userId, UUID taskId) {
        var query = "DELETE FROM tasks WHERE user_id = ? AND task_id = ?";
        session.execute(query, userId, taskId);
    }
}