package com.example.demoprimero.repository;

import com.example.demoprimero.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    @Modifying
    @Query("UPDATE Customer c SET c.isActive = :isActive WHERE c.id = :id")
    void updateActiveStatus(Long id, boolean isActive);
}

