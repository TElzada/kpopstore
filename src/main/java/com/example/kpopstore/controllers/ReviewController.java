package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.entities.Review;
import com.example.kpopstore.services.AlbumService;
import com.example.kpopstore.services.UserService;
import com.example.kpopstore.entities.User;
import com.example.kpopstore.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final AlbumService albumService;
    private final UserService userService;
    @Autowired
    public ReviewController(ReviewService reviewService, AlbumService albumService, UserService userService) {
        this.reviewService = reviewService;
        this.albumService = albumService;
        this.userService = userService;
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable UUID reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<Review>> getReviewsForAlbum(@PathVariable UUID albumId) {
        List<Review> reviews = reviewService.getReviewsForAlbum(albumId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<Review> createReview(
            @RequestParam String comment,
            @RequestParam int rating,
            @RequestParam UUID albumId,
            @RequestParam UUID userId) {

        Album album = albumService.getAlbumById(albumId);
        User user = userService.getUserById(userId);

        Review createdReview = reviewService.createReview(comment, rating, album, user);

        return ResponseEntity.ok(createdReview);
    }


    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable UUID reviewId,
            @RequestParam String comment,
            @RequestParam int rating) {

        Review updatedReview = reviewService.updateReview(reviewId, comment, rating);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}

