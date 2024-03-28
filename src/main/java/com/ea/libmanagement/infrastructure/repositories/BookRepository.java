package com.ea.libmanagement.infrastructure.repositories;

import com.ea.libmanagement.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleAndPublisherAndEditionYearAndPublicationYear(String title, String publisher, int editionYear, int publicationYear);

    List<Book> findByTitle(String title);

}
