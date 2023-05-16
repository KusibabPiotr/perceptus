package com.perceptus.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perceptus.library.models.dto.BookDto;
import com.perceptus.library.services.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class LibraryControllerTest {
    @Mock
    private LibraryService service;
    @InjectMocks
    private LibraryController controller;
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetBooks() throws Exception {
        //given
        Page<BookDto> books = Page.empty();
        //when&then
        when(service.getBooks(0,10)).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveBook() throws Exception {
        //given
        BookDto dto = new BookDto("Harry Piotter", "Dunny Annime");
        //when&then
        when(service.saveBook(dto)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBook() throws Exception {
        //given
        Long bookId = 1L;
        BookDto dto = new BookDto("Test Book", "Dunny Annime");
        //when&then
        when(service.updateBook(bookId, dto)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Book"));
    }

    @Test
    void testDeleteBook() throws Exception {
        Long bookId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{bookId}", bookId))
                .andExpect(status().is(200));

    }
}