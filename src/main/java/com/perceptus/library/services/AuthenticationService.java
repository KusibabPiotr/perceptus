package com.perceptus.library.services;

import com.perceptus.library.exceptions.EmailNotFoundException;
import com.perceptus.library.mappers.RegistrationRequestMapper;
import com.perceptus.library.models.domain.Token;
import com.perceptus.library.models.domain.TokenType;
import com.perceptus.library.models.dto.AuthenticationRequestDto;
import com.perceptus.library.models.domain.AuthenticationResponse;
import com.perceptus.library.models.domain.User;
import com.perceptus.library.models.dto.RegisterRequestDto;
import com.perceptus.library.repositories.TokenRepository;
import com.perceptus.library.repositories.UserRepository;
import com.perceptus.library.validation.PasswordEqualityValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RegistrationRequestMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEqualityValidator validator;

    @Transactional
    public String register(RegisterRequestDto request) {
        validator.validate(request.password(), request.repeatPassword());
        User user = mapper.mapRegistrationRequestToUser(request);
        user = userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        saveUserToken(user, jwt);
        return "Successfull registration!";
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(EmailNotFoundException::new);
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
