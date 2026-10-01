package com.examly.springapp.service;

import com.examly.springapp.model.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Orders addOrder(Orders order);
    List<Orders> viewAllOrders();
    List<Orders> viewOrderByCustomerId(Long customerId);
    List<Orders> viewOrderByUserId(Long userId);
    Optional<Orders> viewOrderById(Long orderId);
    boolean deleteOrder(Long orderId);
    boolean cancelOrder(Long orderId);
}
