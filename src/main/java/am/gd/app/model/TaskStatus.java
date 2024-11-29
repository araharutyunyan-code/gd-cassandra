package am.gd.app.model;

public enum TaskStatus {
    NEW,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    public String toDisplayString() {
        return switch (this) {
            case NEW -> "New Task";
            case IN_PROGRESS -> "In Progress";
            case COMPLETED -> "Completed";
            case CANCELLED -> "Cancelled";
        };
    }
}