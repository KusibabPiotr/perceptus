package com.perceptus.library.security.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecurityConfigurationTest {

    @Autowired
    @Qualifier("mine")
    private SecurityFilterChain chain;

    @Test
    public void testSecurityFilterChain(){
        Optional<String> myFilter = chain.getFilters().stream().map(Object::toString).filter(e -> e.contains("JwtAuthenticationFilter")).findFirst();
        assertThat(chain).isNotNull();
        assertThat(myFilter).isNotEmpty();
    }
}