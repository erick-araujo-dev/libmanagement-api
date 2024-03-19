package com.ea.libmanagement.infrastructure.repositories;

import com.ea.libmanagement.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
