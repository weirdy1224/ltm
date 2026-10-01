package com.examly.springapp.service;

import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.model.Cart;
import com.examly.springapp.model.Customer;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.CartRepo;
import com.examly.springapp.repository.CustomerRepo;
import com.examly.springapp.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public User registerUser(User user) {
        logger.info("Registering user with email: {}, role: {}", user.getEmail(), user.getRole());
        if (userRepo.existsByEmail(user.getEmail())) {
            logger.warn("User registration failed: Email {} already exists", user.getEmail());
            throw new RuntimeException("Email already registered!");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("CUSTOMER");
        } else {
            user.setRole(user.getRole().toUpperCase());
        }

        User savedUser = userRepo.save(user);
        logger.info("Successfully registered user with ID: {}", savedUser.getUserId());

        // Auto-create Customer and Cart record if role is CUSTOMER
        if ("CUSTOMER".equalsIgnoreCase(savedUser.getRole())) {
            Customer customer = new Customer();
            customer.setCustomerName(savedUser.getUsername() != null ? savedUser.getUsername() : "Customer");
            customer.setInformation(savedUser.getMobileNumber() != null ? savedUser.getMobileNumber() : "");
            customer.setUser(savedUser);
            Customer savedCustomer = customerRepo.save(customer);

            Cart cart = new Cart();
            cart.setCustomer(savedCustomer);
            cart.setTotalAmount(0.0);
            cartRepo.save(cart);
            logger.info("Created linked Customer (ID: {}) and Cart for User ID: {}", savedCustomer.getCustomerId(), savedUser.getUserId());
        }

        return savedUser;
    }

    @Override
    public Map<String, Object> loginUser(LoginDTO loginDTO) {
        logger.info("Attempting login for email: {}", loginDTO.getEmail());
        Optional<User> userOpt = userRepo.findByEmail(loginDTO.getEmail());

        if (userOpt.isEmpty()) {
            logger.error("Login failed: User not found with email {}", loginDTO.getEmail());
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()) &&
            !loginDTO.getPassword().equals(user.getPassword())) {
            logger.error("Login failed: Invalid password for email {}", loginDTO.getEmail());
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole(), user.getUserId());
        logger.info("User {} logged in successfully. Role: {}", user.getEmail(), user.getRole());

        // Find customer ID if customer role
        Long customerId = null;
        Optional<Customer> customerOpt = customerRepo.findByUserUserId(user.getUserId());
        if (customerOpt.isPresent()) {
            customerId = customerOpt.get().getCustomerId();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("jwtToken", token);
        response.put("role", user.getRole());
        response.put("userRole", user.getRole());
        response.put("userId", user.getUserId());
        response.put("email", user.getEmail());
        response.put("username", user.getUsername());
        response.put("customerId", customerId);

        return response;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepo.findById(userId);
    }
}
