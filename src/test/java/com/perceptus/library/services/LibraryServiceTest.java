package com.perceptus.library.services;

import com.perceptus.library.exceptions.BookNotFoundException;
import com.perceptus.library.mappers.BookMapper;
import com.perceptus.library.models.domain.Book;
import com.perceptus.library.models.dto.BookDto;
import com.perceptus.library.repositories.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class LibraryServiceTest {
    @Mock
    private LibraryRepository repository;
    @Mock
    private BookMapper mapper;
    @InjectMocks
    private LibraryService service;

    @Test
    public void testGetBooks(){
        //given
        List<Book> list = new ArrayList<>();
        Book book = new Book(1L,"Title", "Author");
        list.add(book);
        PageImpl<Book> bookPage = new PageImpl<>(list, PageRequest.of(0, 5), list.size());
        when(repository.findAll((Pageable) any())).thenReturn(bookPage);
        //when
        Page<BookDto> books = service.getBooks(0,5);
        //then
        assertThat(books).hasSize(1);
    }

    @Test
    public void testSaveBook(){
        //given
        Book book = new Book(1L,"Title", "Author");
        BookDto bookDto = new BookDto("Title", "Author");
        when(repository.save(book)).thenReturn(book);
        when(mapper.mapBookToDto(book)).thenReturn(bookDto);
        when(mapper.mapDtoToBook(bookDto)).thenReturn(book);
        //when
        BookDto dto = service.saveBook(bookDto);
        //then
        assertThat(dto).isNotNull();
        assertThat(dto.title()).isEqualTo("Title");
        assertThat(dto.author()).isEqualTo("Author");
    }

    @Test
    public void testUpdateBookBookNotFound(){
        //given
        Long id = 1L;
        BookDto bookDto = new BookDto("Title", "Author");
        when(repository.findById(id)).thenThrow(BookNotFoundException.class);
        //when&then
        assertThrows(BookNotFoundException.class, () -> service.updateBook(id, bookDto));
    }

    @Test
    public void testUpdateBook(){
        //given
        Book book = new Book(2L,"Title1", "Author1");
        Book updatedBook = new Book(2L,"Title1U", "Author1U");
        BookDto bookDto = new BookDto("Title1", "Author1");
        BookDto updatedDto = new BookDto("Title1U", "Author1U");
        when(repository.save(book)).thenReturn(book);
        when(mapper.mapBookToDto(book)).thenReturn(bookDto);
        when(mapper.mapDtoToBook(bookDto)).thenReturn(book);
        service.saveBook(bookDto);

        when(repository.findById(2L)).thenReturn(Optional.of(book));
        when(mapper.mapBookToDto(updatedBook)).thenReturn(updatedDto);

        BookDto result = service.updateBook(2L, bookDto);
        //when&then
        assertThat(result).isNotNull();
        assertThat(result.author()).isEqualTo("Author1U");
        assertThat(result.title()).isEqualTo("Title1U");
    }

    @Test
    public void testDeleteBook(){
        //given
        Book book = new Book(2L,"Title1123", "Author123");
        BookDto bookDto = new BookDto("Title123", "Author123");
        when(repository.save(book)).thenReturn(book);
        when(mapper.mapBookToDto(book)).thenReturn(bookDto);
        when(mapper.mapDtoToBook(bookDto)).thenReturn(book);
        service.saveBook(bookDto);
        repository.deleteById(2L);

        Page<BookDto> books = service.getBooks(0,5);

        //when&then
        verify(repository,times(1)).deleteById(2L);
        assertThat(books).isNotNull();
    }

}