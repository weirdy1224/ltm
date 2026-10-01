package com.examly.springapp.service;

import com.examly.springapp.model.Cart;
import com.examly.springapp.model.Course;
import com.examly.springapp.model.Customer;
import com.examly.springapp.repository.CartRepo;
import com.examly.springapp.repository.CourseRepo;
import com.examly.springapp.repository.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private CustomerRepo customerRepo;

    private void recalculateTotal(Cart cart) {
        double total = 0.0;
        if (cart.getCourses() != null) {
            for (Course course : cart.getCourses()) {
                if (course.getCoursePrice() != null) {
                    total += course.getCoursePrice();
                }
            }
        }
        cart.setTotalAmount(total);
    }

    @Override
    public Cart createCart(Cart cart) {
        logger.info("Creating cart");
        if (cart.getCustomer() != null && cart.getCustomer().getCustomerId() != null) {
            Optional<Customer> custOpt = customerRepo.findById(cart.getCustomer().getCustomerId());
            custOpt.ifPresent(cart::setCustomer);
        }
        recalculateTotal(cart);
        Cart saved = cartRepo.save(cart);
        logger.info("Saved cart ID: {}", saved.getCartId());
        return saved;
    }

    @Override
    public Cart addToCart(Long customerId, Long courseId) {
        logger.info("Adding course ID {} to cart for customer ID {}", courseId, customerId);

        Cart cart = cartRepo.findByCustomerCustomerId(customerId)
                .orElseGet(() -> {
                    Customer customer = customerRepo.findById(customerId)
                            .orElseThrow(() -> new RuntimeException("Customer not found ID: " + customerId));
                    Cart newCart = new Cart();
                    newCart.setCustomer(customer);
                    return cartRepo.save(newCart);
                });

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found ID: " + courseId));

        if (cart.getCourses() == null) {
            cart.setCourses(new ArrayList<>());
        }
        cart.getCourses().add(course);
        recalculateTotal(cart);
        Cart updated = cartRepo.save(cart);
        logger.info("Added course {} to cart {}. Total price: {}", courseId, updated.getCartId(), updated.getTotalAmount());
        return updated;
    }

    @Override
    public Cart updateCart(Long cartId, Cart cartDetails) {
        logger.info("Updating cart ID: {}", cartId);
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        if (cartDetails.getCourses() != null) {
            cart.setCourses(cartDetails.getCourses());
        }
        recalculateTotal(cart);
        return cartRepo.save(cart);
    }

    @Override
    public Cart removeCourseFromCart(Long cartId, Long courseId) {
        logger.info("Removing course ID {} from cart ID {}", courseId, cartId);
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        if (cart.getCourses() != null) {
            cart.getCourses().removeIf(c -> c.getCourseId().equals(courseId));
        }
        recalculateTotal(cart);
        return cartRepo.save(cart);
    }

    @Override
    public Cart removeAllCourses(Long cartId) {
        logger.info("Removing all courses from cart ID {}", cartId);
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        cart.setCourses(new ArrayList<>());
        cart.setTotalAmount(0.0);
        return cartRepo.save(cart);
    }

    @Override
    public Optional<Cart> getCartById(Long cartId) {
        logger.debug("Fetching cart by ID: {}", cartId);
        return cartRepo.findById(cartId);
    }

    @Override
    public Optional<Cart> getCartByCustomerId(Long customerId) {
        logger.debug("Fetching cart by customer ID: {}", customerId);
        return cartRepo.findByCustomerCustomerId(customerId);
    }

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        logger.debug("Fetching cart by user ID: {}", userId);
        return cartRepo.findByCustomerUserUserId(userId);
    }

    @Override
    public List<Cart> getAllCarts() {
        logger.debug("Fetching all carts");
        return cartRepo.findAll();
    }
}
