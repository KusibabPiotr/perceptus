package com.perceptus.library.mapper;

import com.perceptus.library.model.domain.Book;
import com.perceptus.library.model.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book mapDtoToBook(final BookDto dto) {
        return Book.builder()
                .title(dto.title())
                .author(dto.author())
                .build();
    }
    public BookDto mapBookToDto(final Book book) {
        return BookDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
