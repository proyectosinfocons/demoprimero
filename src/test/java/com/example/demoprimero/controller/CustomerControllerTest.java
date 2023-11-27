package com.example.demoprimero.controller;

import com.example.demoprimero.model.Customer;
import com.example.demoprimero.service.CustomerService;
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
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;
    private Long customerId = 1L;

    @BeforeEach
    void setUp() {
        customer = new Customer();
    }

    @Test
    void testCreateCustomer() {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(response.getBody(), customer),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testGetCustomerById() {
        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(customer));

        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(response.getBody(), customer),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerService.getCustomerById(customerId)).thenReturn(Optional.empty());

        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);

        assertEquals(response.getStatusCodeValue(), 404);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        assertAll(
                () -> assertNotNull(response),
                () -> assertFalse(response.getBody().isEmpty()),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testUpdateCustomer() {
        when(customerService.updateCustomer(eq(customerId), any(Customer.class), eq(authentication))).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.updateCustomer(customerId, customer, authentication);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(response.getBody(), customer),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerService).deleteCustomerLogically(customerId, authentication);

        ResponseEntity<Void> response = customerController.deleteCustomer(customerId, authentication);

        assertEquals(response.getStatusCodeValue(), 200);
    }

}
