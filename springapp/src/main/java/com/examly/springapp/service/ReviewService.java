package com.examly.springapp.service;

import com.examly.springapp.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getAllReviews();
    Optional<Review> getReviewById(Long reviewId);
    List<Review> getReviewsByUserId(Long userId);
    List<Review> getReviewsByCustomerId(Long customerId);
    boolean deleteReview(Long reviewId);
}
