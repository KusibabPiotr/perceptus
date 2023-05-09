package com.perceptus.library.mapper;

import com.perceptus.library.model.domain.Book;
import com.perceptus.library.model.dto.BookDto;

public class BookMapper {
    public static Book mapDtoToBook(final BookDto dto) {
        return Book.builder()
                .id(dto.id())
                .title(dto.title())
                .author(dto.author())
                .build();
    }
    public static BookDto mapBookToDto(final Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
