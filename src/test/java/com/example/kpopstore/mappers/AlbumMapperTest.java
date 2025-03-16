package com.example.kpopstore.mappers;

import com.example.kpopstore.DTO.AlbumDTO;
import com.example.kpopstore.entities.Album;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AlbumMapperTest {

    @Test
    void testToDTO() {
        Album album = new Album("Wings", "BTS", LocalDate.of(2016, 10, 10), BigDecimal.valueOf(20.99), 100, "2nd FULL-LENGTH ALBUM", "cover1.jpg");
        album.setId(UUID.randomUUID());

        AlbumDTO albumDTO = AlbumMapper.toDTO(album);

        assertNotNull(albumDTO);
        assertEquals(album.getId(), albumDTO.getId());
        assertEquals(album.getTitle(), albumDTO.getTitle());
        assertEquals(album.getArtist(), albumDTO.getArtist());
        assertEquals(album.getReleaseDate(), albumDTO.getReleaseDate());
        assertEquals(album.getPrice(), albumDTO.getPrice());
    }

    @Test
    void testToEntity() {
        String id = UUID.randomUUID().toString();

        AlbumDTO albumDTO = new AlbumDTO(id, "Wings", "BTS", LocalDate.of(2016, 10, 10),
                BigDecimal.valueOf(20.99), 100, "2nd FULL-LENGTH ALBUM", "cover1.jpg");

        Album album = AlbumMapper.toEntity(albumDTO);

        assertNotNull(album);
        assertEquals(albumDTO.getTitle(), album.getTitle());
        assertEquals(albumDTO.getArtist(), album.getArtist());
        assertEquals(albumDTO.getReleaseDate(), album.getReleaseDate());
        assertEquals(albumDTO.getPrice(), album.getPrice());
    }

}
