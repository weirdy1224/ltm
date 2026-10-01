package com.examly.springapp.controller;

import com.examly.springapp.model.Customer;
import com.examly.springapp.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        logger.info("REST request to register customer: {}", customer.getCustomerName());
        try {
            Customer savedCustomer = customerService.registerCustomer(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating customer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId) {
        logger.info("REST request to get customer by ID: {}", customerId);
        Optional<Customer> customerOpt = customerService.viewCustomerById(customerId);
        if (customerOpt.isPresent()) {
            return ResponseEntity.ok(customerOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Customer not found with id: " + customerId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCustomerByUserId(@PathVariable Long userId) {
        logger.info("REST request to get customer by User ID: {}", userId);
        Optional<Customer> customerOpt = customerService.viewCustomerByUserId(userId);
        if (customerOpt.isPresent()) {
            return ResponseEntity.ok(customerOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Customer not found for user id: " + userId));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        logger.info("REST request to get all customers");
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
        logger.info("REST request to update customer ID: {}", customerId);
        try {
            Customer updated = customerService.updateCustomer(customerId, customer);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
