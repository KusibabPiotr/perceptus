package com.perceptus.library.service;

import com.perceptus.library.mapper.RegistrationRequestMapper;
import com.perceptus.library.model.domain.*;
import com.perceptus.library.model.dto.AuthenticationRequestDto;
import com.perceptus.library.model.dto.RegisterRequestDto;
import com.perceptus.library.repository.TokenRepository;
import com.perceptus.library.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private RegistrationRequestMapper mapper;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Captor
    private ArgumentCaptor<Token> tokenArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // Given
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Adam","Kacz","test@gmail.com", "password123", Role.USER);
        User user = new User(1, "Adam","Kacz","test@gmail.com", "password123",Role.USER, new ArrayList<>());
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        when(mapper.mapRegistrationRequestToUser(registerRequestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(accessToken, refreshToken);

        // When
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequestDto);

        // Then
        verify(userRepository, times(1)).save(user);
        verify(jwtService, times(2)).generateToken(user);
        assertThat(authenticationResponse.getAccessToken()).isEqualTo(accessToken);
        assertThat(authenticationResponse.getRefreshToken()).isEqualTo(refreshToken);
        verify(tokenRepository, times(1)).save(tokenArgumentCaptor.capture());
        assertThat(tokenArgumentCaptor.getValue().getToken()).isEqualTo(accessToken);
        assertThat(tokenArgumentCaptor.getValue().getTokenType()).isEqualTo(BEARER);
        assertThat(tokenArgumentCaptor.getValue().isExpired()).isFalse();
        assertThat(tokenArgumentCaptor.getValue().isRevoked()).isFalse();
        assertThat(authenticationResponse).isEqualToComparingFieldByField(expectedResponse);
    }

    @Test
    void testAuthenticate() {
        // Given
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto("test@gmail.com", "password123");
        User user = new User(1, "Adam","Kacz","test@gmail.com", "password123",Role.USER, new ArrayList<>());
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
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
        assertThat(authenticationResponse.getRefreshToken()).isEqualTo(refreshToken);
        verify(userRepository, times(1)).findByEmail("test@gmail.com");
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtService, times(2)).generateToken(user);
        verify(tokenRepository, times(1)).save(any(Token.class));
        verify(tokenRepository, times(1)).findAllValidTokenByUser(user.getId());
        assertThat(authenticationResponse).isEqualToComparingFieldByField(expectedResponse);
    }
}


