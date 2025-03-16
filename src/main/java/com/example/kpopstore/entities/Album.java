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

    public Album(String title, String artist, LocalDate releaseDate, BigDecimal price, int stock, String
