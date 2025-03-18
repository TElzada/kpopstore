package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.ReviewDTO;
import com.example.kpopstore.entities.Review;
import com.example.kpopstore.entities.User;
import com.example.kpopstore.entities.Album;
import com.example.kpopstore.services.UserService;
import com.example.kpopstore.services.AlbumService;


public class ReviewMapper {

    private final UserService userService;
    private final AlbumService albumService;


    public ReviewMapper(UserService userService, AlbumService albumService) {
        this.userService = userService;
        this.albumService = albumService;
    }

    public static ReviewDTO toDTO(Review review) {
        if (review == null) {
            return null;
        }
        return new ReviewDTO(
                review.getId(),
                review.getComment(),
                review.getRating(),
                review.getDate(),
                review.getAlbum().getId(),
                review.getUser().getId()
        );
    }

    public Review toEntity(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            return null;
        }


        User user = userService.getUserById(reviewDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }


        Album album = albumService.getAlbumById(reviewDTO.getAlbumId());
        if (album == null) {
            throw new RuntimeException("Album not found");
        }


        return new Review(
                reviewDTO.getComment(),
                reviewDTO.getRating(),
                reviewDTO.getDate(),
                album,
                user
        );
    }
}

