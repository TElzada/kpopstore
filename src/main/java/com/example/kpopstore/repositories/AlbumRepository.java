package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {
    List<Album> findByTitleContainingIgnoreCase(String title);

    // Поиск альбомов по артисту
    List<Album> findByArtistContainingIgnoreCase(String artist);

    // Получить альбомы, отсортированные по дате релиза (от новых к старым)
    List<Album> findAllByOrderByReleaseDateDesc();

    // Найти альбом по ID (этот метод уже предоставляется JpaRepository, но можно его уточнить)
    Optional<Album> findById(UUID id);
}

