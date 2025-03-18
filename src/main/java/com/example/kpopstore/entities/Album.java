package com.example.kpopstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Artist cannot be blank")
    private String artist;

    private LocalDate releaseDate;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to zero")
    private BigDecimal price;

    @Min(value = 0, message = "Stock must be greater than or equal to zero")
    private int stock;

    private String description;
    private String coverImage;

    @OneToMany(mappedBy = "album")
    private List<Review> reviews;

    @OneToMany(mappedBy = "album")
    private List<OrderItem> orderItems;

    public Album() {}

    public Album(String title, String artist, LocalDate releaseDate, BigDecimal price, int stock, String description, String coverImage) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.coverImage = coverImage;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}