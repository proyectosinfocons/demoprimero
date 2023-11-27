package com.example.demoprimero.controller;

import com.example.demoprimero.model.Transaction;
import com.example.demoprimero.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;
    private Long transactionId = 1L;
    private Long accountId = 1L;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
    }

    @Test
    void testCreateTransaction() {
        when(transactionService.createTransaction(any(Transaction.class), eq(authentication))).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction, authentication);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(response.getBody(), transaction),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testGetTransactionById() {
        when(transactionService.getTransactionById(transactionId, authentication)).thenReturn(Optional.of(transaction));

        ResponseEntity<Transaction> response = transactionController.getTransactionById(transactionId, authentication);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(response.getBody(), transaction),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testGetTransactionById_NotFound() {
        when(transactionService.getTransactionById(transactionId, authentication)).thenReturn(Optional.empty());

        ResponseEntity<Transaction> response = transactionController.getTransactionById(transactionId, authentication);

        assertEquals(response.getStatusCodeValue(), 404);
    }

    @Test
    void testGetTransactionsByAccountId() {
        List<Transaction> transactions = Arrays.asList(transaction);
        when(transactionService.getTransactionsByAccountId(accountId, authentication)).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByAccountId(accountId, authentication);

        assertAll(
                () -> assertNotNull(response),
                () -> assertFalse(response.getBody().isEmpty()),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }
}
