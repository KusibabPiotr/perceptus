package com.perceptus.library.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Test
    public void testUserDetailService(){
        UserDetails userDetails = service.loadUserByUsername("Any");
        assertThat(service).isNotNull();
        assertThat(userDetails).isNotNull();
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
}