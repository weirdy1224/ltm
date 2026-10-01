package com.examly.springapp.repository;

import com.examly.springapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserUserId(Long userId);
    Optional<Customer> findByUserEmail(String email);
}
