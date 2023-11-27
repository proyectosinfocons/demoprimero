package com.example.demoprimero.controller;

import com.example.demoprimero.model.Account;
import com.example.demoprimero.service.AccountService;
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
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AccountController accountController;

    private Account account;
    private Long accountId = 1L;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void testCreateAccount() {
        when(accountService.createAccount(any(Account.class), eq(authentication))).thenReturn(account);

        ResponseEntity<Account> response = accountController.createAccount(account, authentication);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(response.getBody(), account),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testGetAccountById() {
        when(accountService.getAccountById(accountId, authentication)).thenReturn(Optional.of(account));

        ResponseEntity<Account> response = accountController.getAccountById(accountId, authentication);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(response.getBody(), account),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testGetAccountById_NotFound() {
        when(accountService.getAccountById(accountId, authentication)).thenReturn(Optional.empty());

        ResponseEntity<Account> response = accountController.getAccountById(accountId, authentication);

        assertEquals(response.getStatusCodeValue(), 404);
    }

    @Test
    void testGetAccountsByCustomerId() {
        List<Account> accounts = Arrays.asList(account);
        when(accountService.getAccountsByCustomerId(authentication)).thenReturn(accounts);

        ResponseEntity<List<Account>> response = accountController.getAccountsByCustomerId(authentication);

        assertAll(
                () -> assertNotNull(response),
                () -> assertFalse(response.getBody().isEmpty()),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testUpdateAccount() {
        when(accountService.updateAccount(eq(accountId), any(Account.class), eq(authentication))).thenReturn(account);

        ResponseEntity<Account> response = accountController.updateAccount(accountId, account, authentication);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(response.getBody(), account),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testCloseAccount() {
        doNothing().when(accountService).closeAccount(accountId, authentication);

        ResponseEntity<Void> response = accountController.closeAccount(accountId, authentication);

        assertEquals(response.getStatusCodeValue(), 200);
    }

}
