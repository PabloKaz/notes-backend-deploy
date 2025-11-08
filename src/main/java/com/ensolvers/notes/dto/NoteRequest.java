package com.ensolvers.notes.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record NoteRequest(
    @NotBlank String title,
    String content,
    Set<Long> categoryIds
) {}
