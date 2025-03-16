package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.entities.Review;
import com.example.kpopstore.entities.User;
import com.example.kpopstore.services.AlbumService;
import com.example.kpopstore.services.ReviewService;
import com.example.kpopstore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    @Mock
    private AlbumService albumService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReviewController reviewController;

    private Review review;
    private Album album;
    private User user;

    @BeforeEach
    public void setUp() {

        album = new Album();
        album.setId(UUID.randomUUID());
        album.setTitle("Album Title");

        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("User1");

        review = new Review("Great album!", 5, null, album, user);
        review.setId(UUID.randomUUID());

        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    public void getReviewById_ShouldReturnReview() throws Exception {
        when(reviewService.getReviewById(eq(review.getId()))).thenReturn(review);

        mockMvc.perform(get("/api/reviews/{reviewId}", review.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Great album!"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    public void getReviewsForAlbum_ShouldReturnReviews() throws Exception {
        List<Review> reviews = List.of(review);
        when(reviewService.getReviewsForAlbum(eq(album.getId()))).thenReturn(reviews);

        mockMvc.perform(get("/api/reviews/album/{albumId}", album.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("Great album!"))
                .andExpect(jsonPath("$[0].rating").value(5));
    }

    @Test
    public void createReview_ShouldReturnCreatedReview() throws Exception {
        when(albumService.getAlbumById(eq(album.getId()))).thenReturn(album);
        when(userService.getUserById(eq(user.getId()))).thenReturn(user);
        when(reviewService.createReview(eq("Great album!"), eq(5), eq(album), eq(user))).thenReturn(review);

        mockMvc.perform(post("/api/reviews")
                        .param("comment", "Great album!")
                        .param("rating", "5")
                        .param("albumId", album.getId().toString())
                        .param("userId", user.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Great album!"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    public void updateReview_ShouldReturnUpdatedReview() throws Exception {
        when(reviewService.updateReview(eq(review.getId()), eq("Updated comment"), eq(4)))
                .thenReturn(new Review("Updated comment", 4, null, album, user));

        mockMvc.perform(put("/api/reviews/{reviewId}", review.getId())
                        .param("comment", "Updated comment")
                        .param("rating", "4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Updated comment"))
                .andExpect(jsonPath("$.rating").value(4));
    }

    @Test
    public void deleteReview_ShouldReturnNoContent() throws Exception {
        doNothing().when(reviewService).deleteReview(eq(review.getId()));

        mockMvc.perform(delete("/api/reviews/{reviewId}", review.getId()))
                .andExpect(status().isNoContent());
    }
}

