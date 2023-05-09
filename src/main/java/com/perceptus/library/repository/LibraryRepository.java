package com.perceptus.library.repository;

import com.perceptus.library.model.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Book, Long> {
}
