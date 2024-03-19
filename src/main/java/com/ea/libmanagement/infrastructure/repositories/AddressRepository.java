package com.ea.libmanagement.infrastructure.repositories;

import com.ea.libmanagement.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
