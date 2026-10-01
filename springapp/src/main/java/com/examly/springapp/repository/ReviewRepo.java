package com.examly.springapp.repository;

import com.examly.springapp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findByCustomerUserUserId(Long userId);
    List<Review> findByCustomerCustomerId(Long customerId);
}
