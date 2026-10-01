package com.examly.springapp.service;

import com.examly.springapp.model.Cart;
import com.examly.springapp.model.Customer;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.CartRepo;
import com.examly.springapp.repository.CustomerRepo;
import com.examly.springapp.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public Customer registerCustomer(Customer customer) {
        logger.info("Registering customer: {}", customer.getCustomerName());

        if (customer.getUser() != null && customer.getUser().getUserId() != null) {
            Optional<User> existingUser = userRepo.findById(customer.getUser().getUserId());
            existingUser.ifPresent(customer::setUser);
        }

        Customer saved = customerRepo.save(customer);
        logger.info("Customer registered with ID: {}", saved.getCustomerId());

        // Ensure cart exists for customer
        Optional<Cart> existingCart = cartRepo.findByCustomerCustomerId(saved.getCustomerId());
        if (existingCart.isEmpty()) {
            Cart cart = new Cart();
            cart.setCustomer(saved);
            cart.setTotalAmount(0.0);
            cartRepo.save(cart);
            logger.info("Created cart for new customer ID: {}", saved.getCustomerId());
        }

        return saved;
    }

    @Override
    public Optional<Customer> viewCustomerById(Long customerId) {
        logger.debug("Fetching customer by ID: {}", customerId);
        return customerRepo.findById(customerId);
    }

    @Override
    public Optional<Customer> viewCustomerByUserId(Long userId) {
        logger.debug("Fetching customer by User ID: {}", userId);
        return customerRepo.findByUserUserId(userId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        logger.debug("Fetching all customers");
        return customerRepo.findAll();
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        logger.info("Updating customer with ID: {}", customerId);
        return customerRepo.findById(customerId).map(existing -> {
            if (updatedCustomer.getCustomerName() != null) {
                existing.setCustomerName(updatedCustomer.getCustomerName());
            }
            if (updatedCustomer.getInformation() != null) {
                existing.setInformation(updatedCustomer.getInformation());
            }
            return customerRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
    }
}
