package com.perceptus.library.controller;

import com.perceptus.library.exceptions.BookNotFoundException;
import com.perceptus.library.exceptions.EmailNotFoundException;
import com.perceptus.library.exceptions.PasswordNotMatchException;
import com.perceptus.library.model.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@WebMvcTest(controllers = GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeAll(){

    }

    @Test
    @DisplayName("Test book not found exception handler")
    public void testBookNotFoundExceptionHandler() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("There is no book with given ID in DB!"));
    }

    @Test
    @DisplayName("Test email not found exception handler")
    public void testEmailNotFoundExceptionHandler() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users?email=example@test.com"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("User with given email cannot be found in DB!"));
    }

    @Test
    @DisplayName("Test password not match exception handler")
    public void testPasswordNotMatchExceptionHandler() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("password", "password")
                        .param("repeatPassword", "differentPassword"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Password and repeat password fields should match to each other!!"));
    }

    @Test
    @DisplayName("Test method argument not valid exception handler")
    public void testMethodArgumentNotValidExceptionHandler() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content("{}")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Title is required"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author is required"));
    }
}