package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.AlbumDTO;
import com.example.kpopstore.entities.Album;

import java.util.UUID;

public class AlbumMapper {

    public static AlbumDTO toDTO(Album album) {
        if (album == null) {
            return null;
        }
        return new AlbumDTO(
                album.getId().toString(),  // Преобразуем UUID в строку
                album.getTitle(),
                album.getArtist(),
                album.getReleaseDate(),
                album.getPrice(),
                album.getStock(),
                album.getDescription(),
                album.getCoverImage()
        );
    }

    public static Album toEntity(AlbumDTO albumDTO) {
        if (albumDTO == null) {
            return null;
        }

        UUID id = UUID.fromString(albumDTO.getId());
        return new Album(
                albumDTO.getTitle(),
                albumDTO.getArtist(),
                albumDTO.getReleaseDate(),
                albumDTO.getPrice(),
                albumDTO.getStock(),
                albumDTO.getDescription(),
                albumDTO.getCoverImage()
        );
    }
}

