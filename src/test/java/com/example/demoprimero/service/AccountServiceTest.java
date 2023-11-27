package com.example.demoprimero.service;

import com.example.demoprimero.exceptions.AccountNotFoundException;
import com.example.demoprimero.exceptions.UnauthorizedException;
import com.example.demoprimero.model.Account;
import com.example.demoprimero.model.Customer;
import com.example.demoprimero.repository.AccountRepository;
import com.example.demoprimero.repository.CustomerRepository;
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
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private Customer customer;
    private Long accountId = 1L;
    private String customerEmail = "test@example.com";

    @BeforeEach
    void setUp() {
        account = new Account();
        customer = new Customer();
        customer.setEmail(customerEmail);
        // Configura aquí las propiedades de 'account' y 'customer'
    }

    @Test
    void testCreateAccount() {
        when(authentication.getName()).thenReturn(customerEmail);
        when(customerRepository.findByEmail(customerEmail)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccount(account, authentication);

        assertNotNull(createdAccount);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testCreateAccount_CustomerNotFound() {
        when(authentication.getName()).thenReturn(customerEmail);
        when(customerRepository.findByEmail(customerEmail)).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> accountService.createAccount(account, authentication));
    }

}
