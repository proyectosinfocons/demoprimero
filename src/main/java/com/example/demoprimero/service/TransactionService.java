package com.example.demoprimero.service;

import com.example.demoprimero.exceptions.AccountNotFoundException;
import com.example.demoprimero.exceptions.UnauthorizedException;
import com.example.demoprimero.model.Account;
import com.example.demoprimero.model.Customer;
import com.example.demoprimero.model.Transaction;
import com.example.demoprimero.repository.AccountRepository;
import com.example.demoprimero.repository.CustomerRepository;
import com.example.demoprimero.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction createTransaction(Transaction transaction,  Authentication authentication) {
        Customer customer = obtenerCustomerDesdeAuthentication(authentication);
        Account cuenta = accountRepository
                .findById(transaction.getAccount().getId())
                .orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada"));
        if (!cuenta.getCustomer().getId().equals(customer.getId())) {
            throw new UnauthorizedException("No autorizado para realizar esta transacción");
        }
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransactionById(Long transactionId, Authentication authentication) {
        Customer customer = obtenerCustomerDesdeAuthentication(authentication);
        Account cuenta = accountRepository
                .findById(transactionRepository.findById(transactionId).get().getAccount().getId())
                .orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada"));
        if (!cuenta.getCustomer().getId().equals(customer.getId())) {
            throw new UnauthorizedException("No autorizado para realizar esta transacción");
        }
        return transactionRepository.findById(transactionId);
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId, Authentication authentication) {
        Customer customer = obtenerCustomerDesdeAuthentication(authentication);
        Account cuenta = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada"));
        if (!cuenta.getCustomer().getId().equals(customer.getId())) {
            throw new UnauthorizedException("No autorizado para realizar esta transacción");
        }
        return transactionRepository.findByAccountId(accountId);
    }

    private Customer obtenerCustomerDesdeAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return customerRepository.findByEmail(username)
                .orElseThrow(() -> new UnauthorizedException("Cliente no encontrado"));
    }

}

