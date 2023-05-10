package com.perceptus.library.model.dto;

import lombok.Builder;

@Builder
public record BookDto(
        String title,
        String author
) {
}
