package com.perceptus.library.repositories;

import com.perceptus.library.models.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);
}
