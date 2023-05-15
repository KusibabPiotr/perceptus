package com.perceptus.library.controllers;

import com.perceptus.library.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@WebMvcTest(controllers = GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private GlobalExceptionHandler handler;

    @Test
    public void testBookNotFoundExceptionHandler() {
        //given&when
        ResponseEntity<String> response = handler.handleBookNotFoundException();
        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isEqualTo("There is no book with given ID in DB!");

    }

    @Test
    public void testEmailNotFoundExceptionHandler() {
        //given&when
        ResponseEntity<String> response = handler.handleEmailNotFoundException();
        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isEqualTo("User with given email cannot be found in DB!");
    }

    @Test
    public void testPasswordNotMatchExceptionHandler() {
        //given&when
        ResponseEntity<String> response = handler.handlePasswordNotMatchException();
        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isEqualTo("Password and repeat password fields should match to each other!!");
    }

    @Test
    public void testMethodArgumentNotValidExceptionHandler() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "object");
        bindingResult.addError(new FieldError("object", "field", "error message"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                new MethodParameter(this.getClass().getDeclaredMethods()[0], -1),
                bindingResult);

        ResponseEntity<Object> response = exceptionHandler.handleMethodArgumentNotValid(ex,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                new ServletWebRequest(new MockHttpServletRequest()));
        System.out.println(response.getBody());
        assertThat(HttpStatus.BAD_REQUEST).isEqualTo(response.getStatusCode());
    }
}