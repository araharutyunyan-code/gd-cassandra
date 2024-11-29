package am.gd.app.model;

import java.time.Instant;
import java.util.UUID;

public record Task(
    UUID taskId,
    UUID userId,
    String title,
    String description,
    TaskStatus status,
    Instant createdAt
) {
    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }
}
