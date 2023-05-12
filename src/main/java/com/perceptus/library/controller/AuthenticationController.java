package com.perceptus.library.controller;

import com.perceptus.library.model.dto.AuthenticationRequestDto;
import com.perceptus.library.model.domain.AuthenticationResponse;
import com.perceptus.library.model.dto.RegisterRequestDto;
import com.perceptus.library.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
