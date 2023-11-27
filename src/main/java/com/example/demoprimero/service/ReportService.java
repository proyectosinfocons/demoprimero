package com.example.demoprimero.service;

import com.example.demoprimero.exceptions.AccountNotFoundException;
import com.example.demoprimero.exceptions.UnauthorizedException;
import com.example.demoprimero.model.Account;
import com.example.demoprimero.model.Customer;
import com.example.demoprimero.model.Transaction;
import com.example.demoprimero.repository.AccountRepository;
import com.example.demoprimero.repository.CustomerRepository;
import com.example.demoprimero.repository.TransactionRepository;
import com.example.demoprimero.util.TransactionSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> generateReport(Long accountId,
                                            Long customerId,
                                            Date startDate,
                                            Date endDate,
                                            Authentication authentication) {
        Customer customer = obtenerCustomerDesdeAuthentication(authentication);
        Account cuenta = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada"));
        if (!cuenta.getCustomer().getId().equals(customer.getId())) {
            throw new UnauthorizedException("No autorizado para realizar esta transacción");
        }

        return transactionRepository.findAll(
                Specification.where(TransactionSpecifications.hasAccountId(accountId))
                        .and(TransactionSpecifications.hasCustomerId(customerId))
                        .and(TransactionSpecifications.isWithinDateRange(startDate, endDate))
        );
    }

    private Customer obtenerCustomerDesdeAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return customerRepository.findByEmail(username)
                .orElseThrow(() -> new UnauthorizedException("Cliente no encontrado"));
    }
}
