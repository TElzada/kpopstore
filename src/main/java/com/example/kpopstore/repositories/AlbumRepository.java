package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {
}

