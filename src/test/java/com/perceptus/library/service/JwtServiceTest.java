package com.perceptus.library.service;

import com.perceptus.library.model.domain.AuthenticationResponse;
import com.perceptus.library.model.domain.Role;
import com.perceptus.library.model.domain.User;
import com.perceptus.library.model.dto.RegisterRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtServiceTest {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void testExtractToken(){
        //given
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Adam","Kacz","test9@gmail.com", "password123", "password123");
        AuthenticationResponse registered = authenticationService.register(registerRequestDto);
        //when
        String username = jwtService.extractUsername(registered.getAccessToken());
        //then
        assertThat(username).isEqualTo("test9@gmail.com");
    }

    @Test
    public void testExtractClaim(){
        //given
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Adam","Kacz","test@gmail.com", "password123", "password123");
        AuthenticationResponse registered = authenticationService.register(registerRequestDto);
        //when
        String username = jwtService.extractClaim(registered.getAccessToken(), Claims::getSubject);
        //then
        assertThat(username).isEqualTo("test@gmail.com");
    }

    @Test
    public void testGenerateToken(){
        //given
        User user = new User(1, "Adam","Kacz","test@gmail.com", "password123", Role.USER, new ArrayList<>());
        //when
        String token = jwtService.generateToken(user);
        //then
        assertThat(token).isNotNull();
    }

    @Test
    public void testGenerateTokenWithExtraClaims(){
        //given
        Map<String, Object> map = new HashMap<>();
        map.put("my","my");
        User user = new User(1, "Adam","Kacz","test@gmail.com", "password123", Role.USER, new ArrayList<>());
        //when
        String token = jwtService.generateToken(map,user);
        Claims body = Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode("763979244226452948404D635166546A576E5A7234753778217A25432A462D4A")))
                .build()
                .parseClaimsJws(token)
                .getBody();
        //then
        assertThat(token).isNotNull();
        assertThat(body.get("my")).isEqualTo("my");
    }

    @Test
    public void testIsTokenValidTrue(){
        //given
        User user = new User(1, "Adam","Kacz","test@gmail.com", "password123", Role.USER, new ArrayList<>());
        String token = jwtService.generateToken(user);
        //when
        boolean tokenValid = jwtService.isTokenValid(token, user);
        //then
        assertThat(tokenValid).isTrue();

    }

    @Test
    public void testIsTokenValidFalse(){
        //given
        User user = new User(1, "Adam","Kacz","test@gmail.com", "password123", Role.USER, new ArrayList<>());
        User user1 = new User(1, "Anna","Kaczak","test123@gmail.com", "password1234", Role.USER, new ArrayList<>());
        String token = jwtService.generateToken(user);
        //when
        boolean tokenValid = jwtService.isTokenValid(token, user);
        //then
        assertThat(tokenValid).isTrue();

    }
}