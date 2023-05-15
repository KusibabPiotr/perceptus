package com.perceptus.library.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationConfigTest {

    @Autowired
    private UserDetailsService service;

    @Autowired
    private AuthenticationProvider provider;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate template;

    @Test
    public void testUserDetailService(){
        assertThat(service).isNotNull();
    }

    @Test
    public void testAuthenticationProvider(){
        assertThat(provider).isNotNull();
    }

    @Test
    public void testPasswordEncoder(){
        String pass = "pass";
        String encoded = encoder.encode(pass);
        assertThat(encoder).isNotNull();
        assertThat(encoder.matches(pass,encoded)).isTrue();
    }

    @Test
    public void testAuthenticationManager(){
        assertThat(manager).isNotNull();
    }

    @Test
    public void testObjectMapper(){
        assertThat(mapper).isNotNull();
    }

    @Test
    public void testRestTemplate(){
        assertThat(template).isNotNull();
    }
}