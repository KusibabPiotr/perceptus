package com.perceptus.library.controller;

import com.perceptus.library.model.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryController controller;

    @GetMapping
    public List<BookDto> getBooks() {
        return controller.getBooks();
    }

    @GetMapping(value = "/{bookId}")
    public BookDto getBook(@PathVariable Long bookId) {
        return controller.getBook(bookId);
    }

    @PostMapping
    public BookDto saveBook(@RequestBody BookDto book) {
        return controller.saveBook(book);
    }

    @PutMapping(value = "/bookId")
    public BookDto updateBook(@PathVariable Long bookId) {
        return controller.updateBook(bookId);
    }

    @DeleteMapping(value = "/bookId")
    public boolean deleteBook(@PathVariable Long bookId) {
        return controller.deleteBook(bookId);
    }
}
