package com.ea.libmanagement.infrastructure.repositories;

import com.ea.libmanagement.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
