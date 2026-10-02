package com.examly.springapp.controller;

import com.examly.springapp.model.Cart;
import com.examly.springapp.service.CartService;
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
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<?> addCart(@RequestBody Cart cart) {
        logger.info("REST request to add new cart");
        Cart createdCart = cartService.createCart(cart);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PostMapping("/add/{customerId}/{courseId}")
    public ResponseEntity<?> addCourseToCart(@PathVariable Long customerId, @PathVariable Long courseId) {
        logger.info("REST request to add course {} to customer {} cart", courseId, customerId);
        Cart updatedCart = cartService.addToCart(customerId, courseId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<?> editCart(@PathVariable Long cartId, @RequestBody Cart cartDetails) {
        logger.info("REST request to update cart ID: {}", cartId);
        Cart updatedCart = cartService.updateCart(cartId, cartDetails);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/course/{courseId}")
    public ResponseEntity<?> removeCourseFromCart(@PathVariable Long cartId, @PathVariable Long courseId) {
        logger.info("REST request to remove course {} from cart {}", courseId, cartId);
        Cart updatedCart = cartService.removeCourseFromCart(cartId, courseId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> removeAllCoursesFromCart(@PathVariable Long cartId) {
        logger.info("REST request to clear cart ID: {}", cartId);
        Cart updatedCart = cartService.removeAllCourses(cartId);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        logger.info("REST request to get cart by User ID: {}", userId);
        Optional<Cart> cartOpt = cartService.getCartByUserId(userId);
        if (cartOpt.isPresent()) {
            return ResponseEntity.ok(cartOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Cart doesn't exist for user: " + userId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCartByCustomerId(@PathVariable Long customerId) {
        logger.info("REST request to get cart by Customer ID: {}", customerId);
        Optional<Cart> cartOpt = cartService.getCartByCustomerId(customerId);
        if (cartOpt.isPresent()) {
            return ResponseEntity.ok(cartOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Cart doesn't exist for customer: " + customerId));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCartById(@PathVariable Long cartId) {
        logger.info("REST request to get cart by ID: {}", cartId);
        Optional<Cart> cartOpt = cartService.getCartById(cartId);
        if (cartOpt.isPresent()) {
            return ResponseEntity.ok(cartOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Cart doesn't exist: " + cartId));
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        logger.info("REST request to get all carts");
        return ResponseEntity.ok(cartService.getAllCarts());
    }
}
