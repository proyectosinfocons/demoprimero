package com.example.demoprimero.service;

import com.example.demoprimero.exceptions.UnauthorizedException;
import com.example.demoprimero.model.Account;
import com.example.demoprimero.model.Customer;
import com.example.demoprimero.model.Transaction;
import com.example.demoprimero.repository.AccountRepository;
import com.example.demoprimero.repository.CustomerRepository;
import com.example.demoprimero.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;
    private Account account;
    private Customer customer;
    private Long transactionId = 1L;
    private Long accountId = 1L;
    private String customerEmail = "customer@example.com";

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        account = new Account();
        customer = new Customer();

        account.setId(accountId);
        account.setCustomer(customer);
        transaction.setAccount(account);
        customer.setEmail(customerEmail);
    }

    @Test
    void testCreateTransaction_Unauthorized() {
        when(authentication.getName()).thenReturn(customerEmail);
        when(customerRepository.findByEmail(customerEmail)).thenReturn(Optional.of(customer));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        Customer otroCustomer = new Customer();
        otroCustomer.setId(2L);
        account.setCustomer(otroCustomer);

        assertThrows(UnauthorizedException.class,
                () -> transactionService.createTransaction(transaction, authentication));
    }

}
