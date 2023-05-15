package com.perceptus.library.controller;

import com.perceptus.library.model.dto.AuthenticationRequestDto;
import com.perceptus.library.model.domain.AuthenticationResponse;
import com.perceptus.library.model.dto.RegisterRequestDto;
import com.perceptus.library.service.AuthenticationService;
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
