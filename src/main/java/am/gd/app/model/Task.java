package am.gd.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private UUID taskId;
    private UUID userId;
    private String title;
    private String description;
    private String status;
    private Instant createdAt;
}