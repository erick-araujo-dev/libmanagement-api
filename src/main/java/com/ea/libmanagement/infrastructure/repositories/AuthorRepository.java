package com.ea.libmanagement.infrastructure.repositories;

import com.ea.libmanagement.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByName(String name);
}
