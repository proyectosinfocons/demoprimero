package com.example.demoprimero.repository;

import com.example.demoprimero.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByAccountIdAndDateBetween(Long accountId, Date startDate, Date endDate);

}

