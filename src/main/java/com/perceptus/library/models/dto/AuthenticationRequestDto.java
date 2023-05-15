package com.perceptus.library.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AuthenticationRequestDto(
        @NotNull(message = "This field must not be empty!")
        @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "You have to provide right email format!")
        String email,
        @NotNull(message = "This field must not be empty!")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$", message = "Password must be at least 6 characters long and contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, 1 special character, and no whitespaces.")
        String password
) {
}
