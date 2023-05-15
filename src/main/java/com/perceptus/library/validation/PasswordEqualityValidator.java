package com.perceptus.library.validation;

import com.perceptus.library.exceptions.PasswordNotMatchException;
import org.springframework.stereotype.Component;

@Component
public class PasswordEqualityValidator {
    public boolean validate(final String password, final String repeatedPassword)
            throws PasswordNotMatchException {
        if (!test(password, repeatedPassword)) {
            throw new PasswordNotMatchException();
        } else {
            return true;
        }
    }
    boolean test(final String password, final String repeatedPassword) {
        return password.equals(repeatedPassword);
    }
}
