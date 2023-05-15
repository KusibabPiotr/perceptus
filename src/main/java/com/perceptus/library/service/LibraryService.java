package com.perceptus.library.service;

import com.perceptus.library.exceptions.BookNotFoundException;
import com.perceptus.library.mapper.BookMapper;
import com.perceptus.library.model.domain.Book;
import com.perceptus.library.model.dto.BookDto;
import com.perceptus.library.repository.LibraryRepository;
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
