package com.perceptus.library.mappers;

import com.perceptus.library.models.domain.Role;
import com.perceptus.library.models.domain.User;
import com.perceptus.library.models.dto.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationRequestMapper {
    private final PasswordEncoder passwordEncoder;
    public User mapRegistrationRequestToUser(final RegisterRequestDto dto) {
        return User.builder()
                .firstname(dto.firstName())
                .lastname(dto.lastName())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .build();
    }
}
