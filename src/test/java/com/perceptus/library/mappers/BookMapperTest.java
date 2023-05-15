package com.perceptus.library.mappers;

import com.perceptus.library.models.domain.Book;
import com.perceptus.library.models.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class BookMapperTest {
    @Autowired
    private BookMapper mapper;

    @Test
    public void testMapDtoToBook() {
        //given
        var bookDto = BookDto.builder()
                .title("Title")
                .author("Author")
                .build();
        //when
        Book book = mapper.mapDtoToBook(bookDto);

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
        BookDto bookDto = mapper.mapBookToDto(book);

        //then
        assertThat(book).isNotNull();
        assertThat(bookDto.title()).isEqualTo(book.getTitle());
        assertThat(bookDto.author()).isEqualTo(book.getAuthor());
    }

}