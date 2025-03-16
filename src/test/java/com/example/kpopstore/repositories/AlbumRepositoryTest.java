package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    void testSaveAndFindAlbum() {
        Album album = new Album("Test Album", "Test Artist", LocalDate.of(2024, 1, 1),
                BigDecimal.valueOf(19.99), 10, "Description", "cover.jpg");

        album = albumRepository.save(album);

        Optional<Album> foundAlbum = albumRepository.findById(album.getId());

        assertThat(foundAlbum).isPresent();
        assertThat(foundAlbum.get().getTitle()).isEqualTo("Test Album");
    }
}
