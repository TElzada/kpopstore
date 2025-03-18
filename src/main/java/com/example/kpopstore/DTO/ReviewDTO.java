package com.example.kpopstore.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReviewDTO {
    private UUID id;
    private String comment;
    private int rating;
    private LocalDateTime date;
    private UUID albumId; //
    private UUID userId;

    public ReviewDTO(UUID id, String comment, int rating, LocalDateTime date, UUID albumId, UUID userId) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
        this.albumId = albumId;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UUID getAlbumId() {
        return albumId;
    }

    public void setAlbumId(UUID albumId) {
        this.albumId = albumId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
