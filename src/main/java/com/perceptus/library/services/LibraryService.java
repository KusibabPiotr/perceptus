package com.perceptus.library.services;

import com.perceptus.library.exceptions.BookNotFoundException;
import com.perceptus.library.mappers.BookMapper;
import com.perceptus.library.models.domain.Book;
import com.perceptus.library.models.dto.BookDto;
import com.perceptus.library.repositories.LibraryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository repository;
    private final BookMapper mapper;
    public List<BookDto> getBooks() {
        return repository.findAll().stream().map(mapper::mapBookToDto).toList();
    }

    public BookDto saveBook(final BookDto dto) {
        return mapper.mapBookToDto(repository.save(mapper.mapDtoToBook(dto)));
    }

    @Transactional
    public BookDto updateBook(final Long bookId,BookDto dto) {
        Book book = repository.findById(bookId).orElseThrow(BookNotFoundException::new);
        book.setAuthor(dto.author());
        book.setTitle(dto.title());
        return mapper.mapBookToDto(book);
    }

    public void deleteBook(Long bookId) {
        repository.deleteById(bookId);
    }
}
