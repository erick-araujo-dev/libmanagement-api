package com.ea.libmanagement.infrastructure.repositories;

import com.ea.libmanagement.domain.entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Integer> {
}
