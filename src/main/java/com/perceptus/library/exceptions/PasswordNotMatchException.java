package com.perceptus.library.exceptions;

public class PasswordNotMatchException extends RuntimeException{
    final String description;

    public PasswordNotMatchException(String decsription) {
        this.description = decsription;
    }
}
