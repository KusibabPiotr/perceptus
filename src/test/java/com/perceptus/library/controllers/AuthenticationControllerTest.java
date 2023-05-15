package com.perceptus.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perceptus.library.models.domain.AuthenticationResponse;
import com.perceptus.library.models.dto.AuthenticationRequestDto;
import com.perceptus.library.models.dto.RegisterRequestDto;
import com.perceptus.library.services.AuthenticationService;
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
        RegisterRequestDto request = new RegisterRequestDto("anna", "maria","anna@op.pl","Anna1234!","Anna1234!");
        when(authenticationService.register(any(RegisterRequestDto.class))).thenReturn("Successfull registration!");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testAuthenticateEndpointOk() throws Exception {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("access_token");
        AuthenticationRequestDto request = new AuthenticationRequestDto("testuser", "testpassword");
        when(authenticationService.authenticate(any(AuthenticationRequestDto.class))).thenReturn(authenticationResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }
}