package com.perceptus.library.controller;

import com.perceptus.library.model.dto.BookDto;
import com.perceptus.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.ok(service.getBooks());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto dto) {
        return ResponseEntity.ok(service.saveBook(dto));
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<BookDto> updateBook(@PathVariable(name = "bookId") Long bookId, @RequestBody BookDto dto) {
        return ResponseEntity.ok(service.updateBook(bookId, dto));
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "bookId") Long bookId) {
        service.deleteBook(bookId);
        return ResponseEntity.status(200).build();
    }
}
