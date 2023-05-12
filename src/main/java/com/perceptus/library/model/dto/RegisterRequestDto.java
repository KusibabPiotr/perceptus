package com.perceptus.library.model.dto;

import com.perceptus.library.model.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RegisterRequestDto(
        String firstName,
        String lastName,
        @Email(regexp = "^(.+)@(\\S+)$", message = "You have to provide right email format!")
        @NotNull String email,
        @NotNull
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$", message = "Password must be at least 6 characters long and contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, 1 special character, and no whitespaces.")
        String password,
        String repeatPassword,
        Role role
) {
}
