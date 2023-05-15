package com.perceptus.library.models.dto;

import lombok.Builder;

@Builder
public record BookDto(
        String title,
        String author
) {
}
