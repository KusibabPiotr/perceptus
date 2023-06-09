package com.perceptus.library.mappers;

import com.perceptus.library.models.domain.User;
import com.perceptus.library.models.dto.RegisterRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationRequestMapperTest {

    @Test
    public void testPapRegistrationRequestToUser(){
        //given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .firstName("Piotr")
                .lastName("Pawlak")
                .email("abba@op.pl")
                .password("1234")
                .build();
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        RegistrationRequestMapper mapper = new RegistrationRequestMapper(passwordEncoder);
        when(passwordEncoder.encode("1234")).thenReturn("abcdefg");

        //when
        User user = mapper.mapRegistrationRequestToUser(registerRequestDto);

        //then
        assertThat(user).isNotNull();
        assertThat(user.getFirstname()).isEqualTo("Piotr");
        assertThat(user.getLastname()).isEqualTo("Pawlak");
        assertThat(user.getEmail()).isEqualTo("abba@op.pl");
        assertThat(user.getPassword()).isEqualTo("abcdefg");
    }
}