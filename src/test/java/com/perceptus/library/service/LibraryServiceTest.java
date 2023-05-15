package com.perceptus.library.service;

import com.perceptus.library.exceptions.BookNotFoundException;
import com.perceptus.library.mapper.BookMapper;
import com.perceptus.library.model.domain.Book;
import com.perceptus.library.model.dto.BookDto;
import com.perceptus.library.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        when(repository.findAll()).thenReturn(list);
        //when
        List<BookDto> books = service.getBooks();
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

        List<BookDto> books = service.getBooks();
        books.forEach(System.out::println);

        //when&then
        verify(repository,times(1)).deleteById(2L);
        assertThat(books).isNotNull();
    }

}