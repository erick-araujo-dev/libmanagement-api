package com.ea.libmanagement.infrastructure.repositories;

import com.ea.libmanagement.domain.entities.Copy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CopyRepository extends JpaRepository<Copy, Integer> {
}
