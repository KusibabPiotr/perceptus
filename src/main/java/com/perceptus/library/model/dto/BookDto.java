package com.perceptus.library.model.dto;

import lombok.Builder;

@Builder
public record BookDto(
        Long id,
        String title,
        String author
) {
}
