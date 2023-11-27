package com.example.demoprimero.service;

import com.example.demoprimero.exceptions.UnauthorizedException;
import com.example.demoprimero.exceptions.UsuarioNotFoundException;
import com.example.demoprimero.model.Customer;
import com.example.demoprimero.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Solo el mismo cliente puede actualizar sus datos
    public Customer updateCustomer(Long id, Customer customer, Authentication authentication) {
        Customer cust = customerRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente no encontrado"));

        if (!authentication.getName().equals(customer.getEmail())) {
            throw new UnauthorizedException("No está autorizado para actualizar este cliente");
        }
        cust.setNombre(customer.getNombre());
        cust.setDireccion(customer.getDireccion());
        return customerRepository.save(cust);
    }

    @Transactional
    public void deleteCustomerLogically(Long id, Authentication authentication) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente no encontrado"));

        String usernameActual = authentication.getName();
        if (!customer.getEmail().equals(usernameActual)) {
            throw new UnauthorizedException("No autorizado para eliminar este cliente");
        }
        customerRepository.updateActiveStatus(id, false);
    }

}

