package com.perceptus.library.controllers;

import com.perceptus.library.models.dto.AuthenticationRequestDto;
import com.perceptus.library.models.domain.AuthenticationResponse;
import com.perceptus.library.models.dto.RegisterRequestDto;
import com.perceptus.library.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping(value = "/register")
    public ResponseEntity<String> authenticate(@RequestBody @Valid RegisterRequestDto request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequestDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
