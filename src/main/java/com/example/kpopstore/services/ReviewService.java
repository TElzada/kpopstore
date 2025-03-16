package com.example.kpopstore.services;

import com.example.kpopstore.entities.Review;
import com.example.kpopstore.entities.Album;
import com.example.kpopstore.entities.User;
import com.example.kpopstore.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    public Review createReview(String comment, int rating, Album album, User user) {
        Review review = new Review();
        review.setComment(comment);
        review.setRating(rating);
        review.setDate(LocalDateTime.now());
        review.setAlbum(album);
        review.setUser(user);
        return reviewRepository.save(review);
    }


    public List<Review> getReviewsForAlbum(UUID albumId) {
        return reviewRepository.findAllByAlbumId(albumId);
    }


    public Review getReviewById(UUID reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        return reviewOptional.orElseThrow(() -> new RuntimeException("Review not found with ID: " + reviewId));
    }


    public Review updateReview(UUID reviewId, String comment, int rating) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setComment(comment);
            review.setRating(rating);
            review.setDate(LocalDateTime.now());
            return reviewRepository.save(review);
        }
        throw new RuntimeException("Review not found with ID: " + reviewId);
    }


    public void deleteReview(UUID reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            reviewRepository.delete(reviewOptional.get());
        } else {
            throw new RuntimeException("Review not found with ID: " + reviewId);
        }
    }
}
