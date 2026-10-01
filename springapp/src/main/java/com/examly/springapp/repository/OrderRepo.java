package com.examly.springapp.repository;

import com.examly.springapp.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomerCustomerId(Long customerId);
    List<Orders> findByCustomerUserUserId(Long userId);
}
