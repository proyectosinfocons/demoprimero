package com.example.demoprimero.repository;

import com.example.demoprimero.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByCustomerId(Long customerId);

    // Otros métodos según los requerimientos
}
