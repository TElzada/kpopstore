package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.entities.Review;
import com.example.kpopstore.entities.User;
import com.example.kpopstore.repositories.AlbumRepository;
import com.example.kpopstore.repositories.ReviewRepository;
import com.example.kpopstore.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;

    private UUID reviewId;
    private Review review;
    private Album album;
    private User user;

    @BeforeEach
    public void setUp() {

        album = new Album();
        album.setTitle("Test Album");
        album = albumRepository.save(album);

        user = new User();
        user.setUsername("TestUser");
        user = userRepository.save(user);

        review = new Review("Great album!", 5, LocalDateTime.now(), album, user);
        review = reviewRepository.save(review);
        reviewId = review.getId();
    }

    @Test
    public void getReviewById_ShouldReturnReview() throws Exception {
        mockMvc.perform(get("/api/reviews/{reviewId}", reviewId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Great album!"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    public void createReview_ShouldReturnCreatedReview() throws Exception {
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"comment\": \"Awesome!\", \"rating\": 4, \"albumId\": \"" + album.getId() + "\", \"userId\": \"" + user.getId() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment").value("Awesome!"))
                .andExpect(jsonPath("$.rating").value(4));
    }

    @Test
    public void updateReview_ShouldReturnUpdatedReview() throws Exception {
        mockMvc.perform(put("/api/reviews/{reviewId}", reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"comment\": \"Updated comment\", \"rating\": 4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Updated comment"))
                .andExpect(jsonPath("$.rating").value(4));
    }

    @Test
    public void deleteReview_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/reviews/{reviewId}", reviewId))
                .andExpect(status().isNoContent());

        assertThat(reviewRepository.findById(reviewId)).isEmpty();
    }
}
