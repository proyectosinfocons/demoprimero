package com.example.demoprimero.controller;

import com.example.demoprimero.model.Transaction;
import com.example.demoprimero.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction,
                                                         Authentication authentication) {
        return ResponseEntity.ok(transactionService.createTransaction(transaction, authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id,
                                                          Authentication authentication) {
        return transactionService.getTransactionById(id, authentication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId,
                                                                        Authentication authentication) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId, authentication));
    }

}

