package com.perceptus.library.controller;

import com.perceptus.library.model.dto.BookDto;
import com.perceptus.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService service;

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.status(200).body(service.getBooks());
    }

    @GetMapping(value = "/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long bookId) {
        return ResponseEntity.status(200).body(service.getBook(bookId));
    }

    @PostMapping
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto dto) {
        return ResponseEntity.status(200).body(service.saveBook(dto));
    }

    @PutMapping(value = "/bookId")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long bookId, @RequestBody BookDto dto) {
        return ResponseEntity.status(200).body(service.updateBook(bookId, dto));
    }

    @DeleteMapping(value = "/bookId")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        service.deleteBook(bookId);
        return ResponseEntity.status(200).build();
    }
}
