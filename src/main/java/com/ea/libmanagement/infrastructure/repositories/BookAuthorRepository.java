package com.ea.libmanagement.infrastructure.repositories;


import com.ea.libmanagement.domain.entities.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
}
