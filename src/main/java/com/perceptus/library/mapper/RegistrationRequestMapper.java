package com.perceptus.library.mapper;

import com.perceptus.library.model.domain.Role;
import com.perceptus.library.model.domain.User;
import com.perceptus.library.model.dto.RegisterRequestDto;
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
