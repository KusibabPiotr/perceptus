package com.perceptus.library.services;

import com.perceptus.library.models.domain.Role;
import com.perceptus.library.models.domain.User;
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

    @Test
    public void testUsernameToken(){
        //given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxsb1RhdGFyQG9wLnBsIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTY4NDE1OTE2MCwiZXhwIjoxNjg0MjQ1NTYwfQ.IRrmJ-IiLsspf4xf_rOsgVDEVLijgT_zBxgCetxbKFU";
        //when
        String username = jwtService.extractUsername(token);
        //then
        assertThat(username).isEqualTo("helloTatar@op.pl");
    }

    @Test
    public void testExtractClaim(){
        //given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxsb1RhdGFyQG9wLnBsIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTY4NDE1OTE2MCwiZXhwIjoxNjg0MjQ1NTYwfQ.IRrmJ-IiLsspf4xf_rOsgVDEVLijgT_zBxgCetxbKFU";
        //when
        String username = jwtService.extractClaim(token, Claims::getSubject);
        //then
        assertThat(username).isEqualTo("helloTatar@op.pl");
    }

    @Test
    public void testGenerateToken(){
        //given
        User user = new User(9, "Adam","Kacz","test@gmail.com", "password123", Role.USER, new ArrayList<>());
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
        User user = new User(8, "Adam","Kacz","test@gmail.com", "password123", Role.USER, new ArrayList<>());
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
        User user = new User(6, "Adam","Kacz","test@gmail.com", "password123", Role.USER, new ArrayList<>());
        String token = jwtService.generateToken(user);
        //when
        boolean tokenValid = jwtService.isTokenValid(token, user);
        //then
        assertThat(tokenValid).isTrue();

    }

    @Test
    public void testIsTokenValidFalse(){
        //given
        User user = new User(5, "Adam","Kacz","test@gmail.com", "password123", Role.USER, new ArrayList<>());
        String token = jwtService.generateToken(user);
        //when
        boolean tokenValid = jwtService.isTokenValid(token, user);
        //then
        assertThat(tokenValid).isTrue();

    }
}