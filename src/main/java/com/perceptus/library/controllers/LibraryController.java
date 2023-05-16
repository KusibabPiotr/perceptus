package com.perceptus.library.controllers;

import com.perceptus.library.models.dto.BookDto;
import com.perceptus.library.services.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    public ResponseEntity<Page<BookDto>> getBooks(@RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "5") int pageSize) {
        return ResponseEntity.ok(service.getBooks(pageNumber,pageSize));
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
