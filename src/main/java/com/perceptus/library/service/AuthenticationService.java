package com.perceptus.library.service;

import com.perceptus.library.mapper.RegistrationRequestMapper;
import com.perceptus.library.model.dto.AuthenticationRequestDto;
import com.perceptus.library.model.domain.AuthenticationResponse;
import com.perceptus.library.model.domain.User;
import com.perceptus.library.model.dto.RegisterRequestDto;
import com.perceptus.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final RegistrationRequestMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequestDto request) {
        User user = mapper.mapRegistrationRequestToUser(request);
        repository.save(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
