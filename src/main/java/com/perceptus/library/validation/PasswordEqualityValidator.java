package com.perceptus.library.validation;

import com.perceptus.library.exceptions.PasswordNotMatchException;
import org.springframework.stereotype.Component;

@Component
public class PasswordEqualityValidator {
    public void validate(final String password, final String repeatedPassword)
            throws PasswordNotMatchException {
        if (!test(password, repeatedPassword)) {
            throw new PasswordNotMatchException("Password and repeat password fields not match!");
        }
    }
    boolean test(final String password, final String repeatedPassword) {
        return password.equals(repeatedPassword);
    }
}
