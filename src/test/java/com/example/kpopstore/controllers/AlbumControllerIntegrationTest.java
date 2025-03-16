package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.repositories.AlbumRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AlbumControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlbumRepository albumRepository;

    private UUID albumId;
    private Album album;

    @BeforeEach
    public void setUp() {
        album = new Album();
        album.setTitle("Test Album");
        album = albumRepository.save(album);
        albumId = album.getId();
    }

    @Test
    public void getAlbumById_ShouldReturnAlbum() throws Exception {
        mockMvc.perform(get("/api/albums/{albumId}", albumId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Album"));
    }

    @Test
    public void createAlbum_ShouldReturnCreatedAlbum() throws Exception {
        mockMvc.perform(post("/api/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Album\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Album"));
    }

    @Test
    public void deleteAlbum_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/albums/{albumId}", albumId))
                .andExpect(status().isNoContent());

        assertThat(albumRepository.findById(albumId)).isEmpty();
    }
}