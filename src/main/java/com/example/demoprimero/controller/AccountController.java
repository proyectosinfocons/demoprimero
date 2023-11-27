package com.example.demoprimero.controller;

import com.example.demoprimero.model.Account;
import com.example.demoprimero.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account,
                                                 Authentication authentication) {
        return ResponseEntity.ok(accountService.createAccount(account, authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id, Authentication authentication) {
        return accountService.getAccountById(id, authentication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomerId(Authentication authentication) {
        return ResponseEntity.ok(accountService.getAccountsByCustomerId(authentication));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id,
                                                 @RequestBody Account account,
                                                 Authentication authentication) {
        return ResponseEntity.ok(accountService.updateAccount(id, account, authentication));
    }


    @PutMapping("/{id}/close")
    public ResponseEntity<Void> closeAccount(@PathVariable Long id,
                                             Authentication authentication) {
        accountService.closeAccount(id, authentication);
        return ResponseEntity.ok().build();
    }
}

