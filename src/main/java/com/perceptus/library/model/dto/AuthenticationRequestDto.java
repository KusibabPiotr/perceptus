package com.perceptus.library.model.dto;

import lombok.Builder;

@Builder
public record AuthenticationRequestDto(
        String email,
        String password
) {
}
