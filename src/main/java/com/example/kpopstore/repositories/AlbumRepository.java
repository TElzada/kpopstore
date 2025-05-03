package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {
    List<Album> findByTitleContainingIgnoreCase(String title);

    List<Album> findByArtistContainingIgnoreCase(String artist);

    List<Album> findAllByOrderByReleaseDateDesc();

    Optional<Album> findById(UUID id);
}

