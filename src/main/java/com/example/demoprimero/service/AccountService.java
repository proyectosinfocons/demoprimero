package com.example.demoprimero.service;

import com.example.demoprimero.exceptions.AccountNotFoundException;
import com.example.demoprimero.exceptions.UnauthorizedException;
import com.example.demoprimero.model.Account;
import com.example.demoprimero.model.Customer;
import com.example.demoprimero.repository.AccountRepository;
import com.example.demoprimero.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Account createAccount(Account account, Authentication authentication) {

        return customerRepository.findByEmail(authentication.getName())
                .map(customer -> {
                    account.setCustomer(customer);
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new UnauthorizedException("Cliente no encontrado"));

    }

    public Optional<Account> getAccountById(Long accountId, Authentication authentication) {
        Customer customer = obtenerCustomerDesdeAuthentication(authentication);
        Account cuenta = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrado con id: " + accountId));
        if (!cuenta.getCustomer().getId().equals(customer.getId())) {
            throw new UnauthorizedException("No autorizado para visualizar esta cuenta");
        }
        return accountRepository.findById(accountId);
    }

    public List<Account> getAccountsByCustomerId(Authentication authentication) {
        Customer customer = obtenerCustomerDesdeAuthentication(authentication);
        return accountRepository.findByCustomerId(customer.getId());
    }

    public Account updateAccount(Long accountId, Account account, Authentication authentication) {
        Customer customer = obtenerCustomerDesdeAuthentication(authentication);
        Account cuenta = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrado con id: " + accountId));
        if (!cuenta.getCustomer().getId().equals(customer.getId())) {
            throw new UnauthorizedException("No autorizado para modificar esta cuenta");
        }
        return accountRepository.save(account);
    }


    @Transactional
    public void closeAccount(Long accountId, Authentication authentication) {

        Customer customer = obtenerCustomerDesdeAuthentication(authentication);
        Account cuenta = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrado con id: " + accountId));
        if (!cuenta.getCustomer().getId().equals(customer.getId())) {
            throw new UnauthorizedException("No autorizado para modificar esta cuenta");
        }
        cuenta.setClosed(true);
        accountRepository.save(cuenta);
    }

    private Customer obtenerCustomerDesdeAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return customerRepository.findByEmail(username)
                .orElseThrow(() -> new UnauthorizedException("Cliente no encontrado"));
    }
}
