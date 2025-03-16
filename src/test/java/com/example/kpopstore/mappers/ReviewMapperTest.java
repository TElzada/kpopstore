package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.ReviewDTO;
import com.example.kpopstore.entities.Album;
import com.example.kpopstore.entities.Review;
import com.example.kpopstore.entities.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

class ReviewMapperTest {

    @Test
    void testToDTO() {
        Review review = new Review("Great album!", 5, LocalDateTime.now(), new Album(), new User());
        review.setId(UUID.randomUUID());

        ReviewDTO reviewDTO = ReviewMapper.toDTO(review);

        assertNotNull(reviewDTO);
        assertEquals(review.getId(), reviewDTO.getId());
        assertEquals(review.getRating(), reviewDTO.getRating());
        assertEquals(review.getComment(), reviewDTO.getComment());
    }

    @Test
    void testToEntity() {
        Long reviewId = 1L;
        Long albumId = 2L;
        Long userId = 3L;

        ReviewDTO reviewDTO = new ReviewDTO(reviewId, "Great album!", 5, LocalDateTime.now(), albumId, userId);
        ReviewMapper reviewMapper = new ReviewMapper(userService, albumService);
        Review review = reviewMapper.toEntity(reviewDTO);

        assertNotNull(review);
        assertEquals(reviewDTO.getComment(), review.getComment());
        assertEquals(reviewDTO.getRating(), review.getRating());
    }

}

