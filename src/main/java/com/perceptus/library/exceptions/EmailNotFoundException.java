package com.perceptus.library.exceptions;

public class EmailNotFoundException extends RuntimeException {
    final String description;

    public EmailNotFoundException(String description) {
        this.description = description;
    }
}
