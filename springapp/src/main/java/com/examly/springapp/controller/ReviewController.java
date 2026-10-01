package com.examly.springapp.controller;

import com.examly.springapp.model.Review;
import com.examly.springapp.service.ReviewService;
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
@RequestMapping("/api/review")
@CrossOrigin(origins = "*")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        logger.info("REST request to add new review");
        try {
            Review created = reviewService.addReview(review);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating review: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllReviews() {
        logger.info("REST request to get all reviews");
        try {
            List<Review> reviews = reviewService.getAllReviews();
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Unable to retrieve reviews"));
        }
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable Long reviewId) {
        logger.info("REST request to get review by ID: {}", reviewId);
        Optional<Review> reviewOpt = reviewService.getReviewById(reviewId);
        if (reviewOpt.isPresent()) {
            return ResponseEntity.ok(reviewOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Review doesn't exist: " + reviewId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getReviewsByUserId(@PathVariable Long userId) {
        logger.info("REST request to get reviews by user ID: {}", userId);
        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        if (reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No reviews found for user: " + userId));
        }
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        logger.info("REST request to delete review ID: {}", reviewId);
        Optional<Review> reviewOpt = reviewService.getReviewById(reviewId);
        if (reviewOpt.isPresent()) {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok(reviewOpt.get());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Unable to delete review"));
    }
}
