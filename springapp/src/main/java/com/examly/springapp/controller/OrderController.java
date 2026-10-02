package com.examly.springapp.controller;

import com.examly.springapp.model.Orders;
import com.examly.springapp.service.OrderService;
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
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Orders order) {
        logger.info("REST request to create new order");
        Orders created = orderService.addOrder(order);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        logger.info("REST request to get all orders");
        List<Orders> orders = orderService.viewAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        logger.info("REST request to get order by ID: {}", orderId);
        Optional<Orders> orderOpt = orderService.viewOrderById(orderId);
        if (orderOpt.isPresent()) {
            return ResponseEntity.ok(orderOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Order doesn't exist: " + orderId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable Long customerId) {
        logger.info("REST request to get orders by customer ID: {}", customerId);
        List<Orders> orders = orderService.viewOrderByCustomerId(customerId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No orders found for customer: " + customerId));
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        logger.info("REST request to get orders by user ID: {}", userId);
        List<Orders> orders = orderService.viewOrderByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        logger.info("REST request to delete order ID: {}", orderId);
        Optional<Orders> orderOpt = orderService.viewOrderById(orderId);
        if (orderOpt.isPresent()) {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok(orderOpt.get());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Unable to delete order"));
    }
}
