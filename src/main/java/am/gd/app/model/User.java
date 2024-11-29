package am.gd.app.model;

import java.util.UUID;

public record User(
    UUID userId,
    String username,
    String email
) {}
