package com.perceptus.library.service;

import com.perceptus.library.exceptions.EmailNotFoundException;
import com.perceptus.library.exceptions.PasswordNotMatchException;
import com.perceptus.library.mapper.RegistrationRequestMapper;
import com.perceptus.library.model.domain.*;
import com.perceptus.library.model.dto.AuthenticationRequestDto;
import com.perceptus.library.model.dto.RegisterRequestDto;
import com.perceptus.library.repository.TokenRepository;
import com.perceptus.library.repository.UserRepository;
import com.perceptus.library.validation.PasswordEqualityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;
import java.util.Optional;

import static com.perceptus.library.model.domain.TokenType.BEARER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEqualityValidator validator;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private RegistrationRequestMapper mapper;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Captor
    private ArgumentCaptor<Token> tokenArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterOk() {
        // Given
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Adam","Kacz","test@gmail.com", "password123", "password123");
        User user = new User(1, "Adam","Kacz","test@gmail.com", "password123",Role.USER, new ArrayList<>());
        String accessToken = "accessToken";

        when(mapper.mapRegistrationRequestToUser(registerRequestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(accessToken);
        when(validator.validate(registerRequestDto.password(),registerRequestDto.repeatPassword())).thenReturn(true);

        // When
         authenticationService.register(registerRequestDto);

        // Then
        verify(userRepository, times(1)).save(user);
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).save(tokenArgumentCaptor.capture());
        assertThat(tokenArgumentCaptor.getValue().getToken()).isEqualTo(accessToken);
        assertThat(tokenArgumentCaptor.getValue().getTokenType()).isEqualTo(BEARER);
        assertThat(tokenArgumentCaptor.getValue().isExpired()).isFalse();
        assertThat(tokenArgumentCaptor.getValue().isRevoked()).isFalse();
    }

    @Test
    void testRegisterPasswordNotMatch() {
        // Given
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Adam","Kacz","test@gmail.com", "password1234", "password123");
        when(validator.validate(registerRequestDto.password(),registerRequestDto.repeatPassword())).thenThrow(PasswordNotMatchException.class);

        // When&then
        assertThrows(PasswordNotMatchException.class, () -> {
            authenticationService.register(registerRequestDto);
        });

    }

    @Test
    void testAuthenticateOk() {
        // Given
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto("test@gmail.com", "password123");
        User user = new User(1, "Adam","Kacz","test@gmail.com", "password123",Role.USER, new ArrayList<>());
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateToken(user)).thenReturn(accessToken);
        when(jwtService.generateToken(user)).thenReturn(refreshToken);
        when(userRepository.findByEmail(authenticationRequestDto.email())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(accessToken, refreshToken);

        // When
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequestDto);

        // Then
        assertThat(authenticationResponse.getAccessToken()).isEqualTo(accessToken);
        verify(userRepository, times(1)).findByEmail("test@gmail.com");
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).save(any(Token.class));
        verify(tokenRepository, times(1)).findAllValidTokenByUser(user.getId());
        assertThat(authenticationResponse).isEqualToComparingFieldByField(expectedResponse);
    }

    @Test
    void testAuthenticateEmailNotFound() {
        // Given
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto("test@gmail.com", "password123");
        when(userRepository.findByEmail("test@gmail.com")).thenThrow(EmailNotFoundException.class);
        when(authenticationManager.authenticate(any())).thenReturn(null);

        // When&then
        assertThrows(EmailNotFoundException.class, () -> {
            authenticationService.authenticate(authenticationRequestDto);
        });
    }
}


