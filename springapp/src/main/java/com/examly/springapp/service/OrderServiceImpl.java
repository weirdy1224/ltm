package com.examly.springapp.service;

import com.examly.springapp.model.Cart;
import com.examly.springapp.model.Course;
import com.examly.springapp.model.Customer;
import com.examly.springapp.model.Orders;
import com.examly.springapp.repository.CartRepo;
import com.examly.springapp.repository.CustomerRepo;
import com.examly.springapp.repository.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public Orders addOrder(Orders order) {
        logger.info("Creating new order for customer");

        if (order.getCustomer() != null && order.getCustomer().getCustomerId() != null) {
            Optional<Customer> custOpt = customerRepo.findById(order.getCustomer().getCustomerId());
            custOpt.ifPresent(order::setCustomer);
        }

        // Calculate total order price if not provided
        if (order.getOrderPrice() == null || order.getOrderPrice() == 0.0) {
            double total = 0.0;
            if (order.getCourses() != null) {
                for (Course course : order.getCourses()) {
                    if (course.getCoursePrice() != null) {
                        total += course.getCoursePrice();
                    }
                }
            }
            order.setOrderPrice(total);
        }

        Orders savedOrder = orderRepo.save(order);
        logger.info("Order placed successfully with ID: {}, Total price: {}", savedOrder.getOrderId(), savedOrder.getOrderPrice());

        // Clear customer's cart after order placement
        if (savedOrder.getCustomer() != null) {
            Optional<Cart> cartOpt = cartRepo.findByCustomerCustomerId(savedOrder.getCustomer().getCustomerId());
            if (cartOpt.isPresent()) {
                Cart cart = cartOpt.get();
                cart.setCourses(new ArrayList<>());
                cart.setTotalAmount(0.0);
                cartRepo.save(cart);
                logger.info("Cleared cart for customer ID: {}", savedOrder.getCustomer().getCustomerId());
            }
        }

        return savedOrder;
    }

    @Override
    public List<Orders> viewAllOrders() {
        logger.debug("Fetching all orders");
        return orderRepo.findAll();
    }

    @Override
    public List<Orders> viewOrderByCustomerId(Long customerId) {
        logger.debug("Fetching orders for customer ID: {}", customerId);
        return orderRepo.findByCustomerCustomerId(customerId);
    }

    @Override
    public List<Orders> viewOrderByUserId(Long userId) {
        logger.debug("Fetching orders for user ID: {}", userId);
        return orderRepo.findByCustomerUserUserId(userId);
    }

    @Override
    public Optional<Orders> viewOrderById(Long orderId) {
        logger.debug("Fetching order by ID: {}", orderId);
        return orderRepo.findById(orderId);
    }

    @Override
    public boolean deleteOrder(Long orderId) {
        logger.info("Deleting order ID: {}", orderId);
        if (orderRepo.existsById(orderId)) {
            orderRepo.deleteById(orderId);
            logger.info("Deleted order ID: {}", orderId);
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelOrder(Long orderId) {
        logger.info("Cancelling order ID: {}", orderId);
        return deleteOrder(orderId);
    }
}
