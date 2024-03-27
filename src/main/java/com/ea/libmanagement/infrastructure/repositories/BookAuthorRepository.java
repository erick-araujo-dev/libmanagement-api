package com.ea.libmanagement.infrastructure.repositories;


import com.ea.libmanagement.domain.entities.Author;
import com.ea.libmanagement.domain.entities.Book;
import com.ea.libmanagement.domain.entities.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    List<BookAuthor> findByBook(Book book);
}
