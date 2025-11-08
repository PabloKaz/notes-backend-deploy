package com.ensolvers.notes.dto;

import java.time.Instant;
import java.util.Set;

public record NoteResponse(
    Long id, String title, String content, boolean archived,
    Instant createdAt, Instant updatedAt, Set<String> categories
) {}
