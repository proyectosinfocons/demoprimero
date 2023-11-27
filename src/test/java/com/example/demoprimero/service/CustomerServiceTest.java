package com.example.demoprimero.service;

import com.example.demoprimero.exceptions.UnauthorizedException;
import com.example.demoprimero.model.Customer;
import com.example.demoprimero.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private Long customerId = 1L;
    private String customerEmail = "customer@example.com";

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(customerId);
        customer.setEmail(customerEmail);
        // Configura aquí las propiedades de 'customer'
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);

        assertNotNull(createdCustomer);
        assertEquals(customer, createdCustomer);
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<Customer> foundCustomer = customerService.getCustomerById(customerId);

        assertTrue(foundCustomer.isPresent());
        assertEquals(customer, foundCustomer.get());
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    void testUpdateCustomer_Unauthorized() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(authentication.getName()).thenReturn("otheruser@example.com");

        assertThrows(UnauthorizedException.class,
                () -> customerService.updateCustomer(customerId, customer, authentication));
    }

    @Test
    void testDeleteCustomerLogically_Authorized() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(authentication.getName()).thenReturn(customerEmail);
        doNothing().when(customerRepository).updateActiveStatus(customerId, false);

        assertDoesNotThrow(() -> customerService.deleteCustomerLogically(customerId, authentication));
    }

    @Test
    void testDeleteCustomerLogically_Unauthorized() {
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(authentication.getName()).thenReturn("otheruser@example.com");

        assertThrows(UnauthorizedException.class,
                () -> customerService.deleteCustomerLogically(customerId, authentication));
    }

}
