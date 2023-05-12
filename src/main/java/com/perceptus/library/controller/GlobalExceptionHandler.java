package com.perceptus.library.controller;

import com.perceptus.library.exceptions.BookNotFoundException;
import com.perceptus.library.exceptions.EmailNotFoundException;
import com.perceptus.library.exceptions.PasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleAllExceptions(BookNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>("There is no book with given ID in DB!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleAllExceptions(EmailNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>("User with given email cannot be found in DB!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<String> handleAllExceptions(Exception PasswordNotMatchException, WebRequest request) {
        return new ResponseEntity<>("Password and repeat password fields should match to each other!!", HttpStatus.BAD_REQUEST);
    }
}
