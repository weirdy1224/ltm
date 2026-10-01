package com.examly.springapp.service;

import com.examly.springapp.model.Cart;
import com.examly.springapp.model.Course;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Cart createCart(Cart cart);
    Cart addToCart(Long customerId, Long courseId);
    Cart updateCart(Long cartId, Cart cartDetails);
    Cart removeCourseFromCart(Long cartId, Long courseId);
    Cart removeAllCourses(Long cartId);
    Optional<Cart> getCartById(Long cartId);
    Optional<Cart> getCartByCustomerId(Long customerId);
    Optional<Cart> getCartByUserId(Long userId);
    List<Cart> getAllCarts();
}
