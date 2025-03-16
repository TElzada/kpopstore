package com.example.kpopstore.DTO;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Long id;
    private String comment;
    private int rating;
    private LocalDateTime date;
    private Long albumId; //
    private Long userId;

    public ReviewDTO(Long id, String comment, int rating, LocalDateTime date, Long albumId, Long userId) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
        this.albumId = albumId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
