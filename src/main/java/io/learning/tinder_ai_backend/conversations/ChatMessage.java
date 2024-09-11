package io.learning.tinder_ai_backend.conversations;

import java.time.LocalDateTime;

public record ChatMessage(
        String message,
        String authorId,
        LocalDateTime messageTime

) {
}
