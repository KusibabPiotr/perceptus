package com.perceptus.library.validation;

import com.perceptus.library.exceptions.PasswordNotMatchException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordEqualityValidatorTest {

    @Autowired
    private PasswordEqualityValidator validator;

    @Test
    public void testPasswordValidatorReturnTrue(){
        //given
        String password = "Password1!";
        String passRepeat = "Password1!";
        //when
        boolean result = validator.validate(password, passRepeat);
        //then
        assertThat(result).isTrue();
    }

    @Test
    public void testPasswordValidatorThrowException(){
        //given
        String password = "Password1!";
        String passRepeat = "Password1!1";
        //when&then
        assertThrows(PasswordNotMatchException.class, () -> {
            validator.validate(password, passRepeat);
        });
    }
}