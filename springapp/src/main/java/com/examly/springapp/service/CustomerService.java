package com.examly.springapp.service;

import com.examly.springapp.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    Optional<Customer> viewCustomerById(Long customerId);
    Optional<Customer> viewCustomerByUserId(Long userId);
    List<Customer> getAllCustomers();
    Customer updateCustomer(Long customerId, Customer customer);
}
