package com.ea.libmanagement.infrastructure.repositories;

import com.ea.libmanagement.domain.entities.Book;
import com.ea.libmanagement.domain.entities.Copy;
import org.hibernate.annotations.SQLSelect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CopyRepository extends JpaRepository<Copy, Integer> {
    List<Copy> findByBook(Book book);
}
