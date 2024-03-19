package com.ea.libmanagement.infrastructure.repositories;

import com.ea.libmanagement.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
