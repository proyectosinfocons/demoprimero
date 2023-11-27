package com.example.demoprimero.service;

import com.example.demoprimero.exceptions.AccountNotFoundException;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReportService reportService;

    private Customer customer;
    private Account account;
    private Transaction transaction;
    private Long accountId = 1L;
    private Long customerId = 1L;
    private Date startDate;
    private Date endDate;
    private String customerEmail = "customer@example.com";

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(customerId);
        customer.setEmail(customerEmail);

        account = new Account();
        account.setId(accountId);
        account.setCustomer(customer);

        transaction = new Transaction();
        startDate = new Date();
        endDate = new Date();
        // Configura aquí las propiedades de 'transaction'
    }

    @Test
    void generateReport_Authorized() {
        when(authentication.getName()).thenReturn(customerEmail);
        when(customerRepository.findByEmail(customerEmail)).thenReturn(Optional.of(customer));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(transaction));

        List<Transaction> report = reportService.generateReport(accountId, customerId, startDate, endDate, authentication);

        assertFalse(report.isEmpty());
        assertEquals(1, report.size());
        assertEquals(transaction, report.get(0));
    }

    @Test
    void generateReport_AccountNotFound() {
        when(authentication.getName()).thenReturn(customerEmail);
        when(customerRepository.findByEmail(customerEmail)).thenReturn(Optional.of(customer));
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> reportService.generateReport(accountId, customerId, startDate, endDate, authentication));
    }

    @Test
    void generateReport_Unauthorized() {
        when(authentication.getName()).thenReturn(customerEmail);
        when(customerRepository.findByEmail(customerEmail)).thenReturn(Optional.of(customer));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        Customer otroCustomer = new Customer();
        otroCustomer.setId(2L);
        account.setCustomer(otroCustomer);

        assertThrows(UnauthorizedException.class,
                () -> reportService.generateReport(accountId, customerId, startDate, endDate, authentication));
    }

    // ... más pruebas para otros casos ...
}
