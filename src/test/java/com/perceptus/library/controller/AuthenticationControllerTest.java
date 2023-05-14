package com.perceptus.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perceptus.library.model.domain.AuthenticationResponse;
import com.perceptus.library.model.dto.AuthenticationRequestDto;
import com.perceptus.library.model.dto.RegisterRequestDto;
import com.perceptus.library.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    @Test
    void testRegisterEndpointOk() throws Exception {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("access_token", "refresh_token");
        RegisterRequestDto request = new RegisterRequestDto("anna", "maria","anna@op.pl","Anna1234!","Anna1234!");
        when(authenticationService.register(any(RegisterRequestDto.class))).thenReturn(authenticationResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testAuthenticateEndpointOk() throws Exception {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("access_token", "refresh_token");
        AuthenticationRequestDto request = new AuthenticationRequestDto("testuser", "testpassword");
        when(authenticationService.authenticate(any(AuthenticationRequestDto.class))).thenReturn(authenticationResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testRefreshTokenEndpointOk() throws Exception {
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        doNothing().when(authenticationService).refreshToken(request, response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/refresh-token"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }
}