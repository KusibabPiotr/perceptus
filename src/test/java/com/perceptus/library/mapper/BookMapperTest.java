package com.perceptus.library.mapper;

import com.perceptus.library.model.domain.Book;
import com.perceptus.library.model.dto.BookDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    @Test
    public void testMapDtoToBook() {
        //given
        var bookDto = BookDto.builder()
                .title("Title")
                .author("Author")
                .build();
        //when
        Book book = BookMapper.mapDtoToBook(bookDto);

        //then
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(bookDto.title());
        assertThat(book.getAuthor()).isEqualTo(bookDto.author());
    }

    @Test
    public void testMapBookToDto() {
        //given
        var book = Book.builder()
                .title("Title")
                .author("Author")
                .build();
        //when
        BookDto bookDto = BookMapper.mapBookToDto(book);

        //then
        assertThat(book).isNotNull();
        assertThat(bookDto.title()).isEqualTo(book.getTitle());
        assertThat(bookDto.author()).isEqualTo(book.getAuthor());
    }

}