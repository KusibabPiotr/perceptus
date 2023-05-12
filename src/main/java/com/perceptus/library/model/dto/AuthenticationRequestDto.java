package com.perceptus.library.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthenticationRequestDto(
        @NotNull(message = "This field must not be empty!")
        String email,
        @NotNull(message = "This field must not be empty!")
        String password
) {
}
