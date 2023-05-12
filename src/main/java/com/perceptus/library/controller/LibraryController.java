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
@PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
public class LibraryController {
    private final LibraryService service;

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.ok(service.getBooks());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create','management:create')")
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto dto) {
        return ResponseEntity.status(201).body(service.saveBook(dto));
    }

    @PutMapping("/bookId")
    @PreAuthorize("hasAnyAuthority('admin:update','management:update')")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long bookId, @RequestBody BookDto dto) {
        return ResponseEntity.status(200).body(service.updateBook(bookId, dto));
    }

    @DeleteMapping("/bookId")
    @PreAuthorize("hasAnyAuthority('admin:delete','management:delete')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        service.deleteBook(bookId);
        return ResponseEntity.status(200).build();
    }
}
