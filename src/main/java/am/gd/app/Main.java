package am.gd.app;

import am.gd.app.config.CassandraDatabaseConnector;
import am.gd.app.model.Task;
import am.gd.app.model.TaskStatus;
import am.gd.app.model.User;
import am.gd.app.repository.TaskManagementRepository;

import java.time.Instant;
import java.util.UUID;


public class Main {
    public static void main(String[] args) {

        var connector = new CassandraDatabaseConnector();
        connector.connect();

        var repository = new TaskManagementRepository(connector.getSession());

        try {
            var userId = UUID.randomUUID();
            var user = new User(userId, "Arayik", "araharutyunyan@griddynamics.com");
            repository.addUser(user);
            System.out.println("User created: " + user.username());

            var task = new Task(
                    UUID.randomUUID(),
                    user.userId(),
                    "Complete Cassandra Q/A project",
                    "Finish the Cassandra implementation",
                    TaskStatus.IN_PROGRESS,
                    Instant.now()
            );
            repository.createTask(task);
            System.out.println("Task created: " + task.title());

            var tasks = repository.getTasksByUserId(user.userId());
            System.out.printf("Found %d tasks%n", tasks.size());

            repository.updateTaskStatus(user.userId(), task.taskId(), TaskStatus.COMPLETED);
            System.out.println("Task status updated");

            var updatedTask = repository.getTaskById(user.userId(), task.taskId())
                    .map(t -> switch (t.status()) {
                        case COMPLETED -> "Task '%s' is done!".formatted(t.title());
                        case IN_PROGRESS -> "Task '%s' is being worked on".formatted(t.title());
                        case NEW -> "Task '%s' hasn't started".formatted(t.title());
                        case CANCELLED -> "Task '%s' was cancelled".formatted(t.title());
                    })
                    .orElse("Task not found");

            System.out.println(updatedTask);

            repository.deleteTask(user.userId(), task.taskId());
            System.out.println("Task deleted");

        } finally {
            connector.close();
        }
    }
}