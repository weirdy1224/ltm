package com.examly.springapp.service;

import com.examly.springapp.model.Customer;
import com.examly.springapp.model.Review;
import com.examly.springapp.repository.CustomerRepo;
import com.examly.springapp.repository.ReviewRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public Review addReview(Review review) {
        logger.info("Adding new review");
        if (review.getCustomer() != null && review.getCustomer().getCustomerId() != null) {
            Optional<Customer> custOpt = customerRepo.findById(review.getCustomer().getCustomerId());
            custOpt.ifPresent(review::setCustomer);
        }
        if (review.getDateCreated() == null) {
            review.setDateCreated(new Date());
        }

        Review saved = reviewRepo.save(review);
        logger.info("Review created with ID: {}", saved.getReviewId());
        return saved;
    }

    @Override
    public List<Review> getAllReviews() {
        logger.debug("Fetching all reviews");
        return reviewRepo.findAll();
    }

    @Override
    public Optional<Review> getReviewById(Long reviewId) {
        logger.debug("Fetching review by ID: {}", reviewId);
        return reviewRepo.findById(reviewId);
    }

    @Override
    public List<Review> getReviewsByUserId(Long userId) {
        logger.debug("Fetching reviews by user ID: {}", userId);
        return reviewRepo.findByCustomerUserUserId(userId);
    }

    @Override
    public List<Review> getReviewsByCustomerId(Long customerId) {
        logger.debug("Fetching reviews by customer ID: {}", customerId);
        return reviewRepo.findByCustomerCustomerId(customerId);
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        logger.info("Deleting review ID: {}", reviewId);
        if (reviewRepo.existsById(reviewId)) {
            reviewRepo.deleteById(reviewId);
            logger.info("Deleted review ID: {}", reviewId);
            return true;
        }
        return false;
    }
}
