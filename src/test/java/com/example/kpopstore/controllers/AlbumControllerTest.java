package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.services.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AlbumService albumService;

    @InjectMocks
    private AlbumController albumController;

    private Album album;

    @BeforeEach
    public void setUp() {
        album = new Album();
        album.setTitle("Album 1");
        album.setArtist("Artist 1");
        album.setReleaseDate(LocalDate.of(2025,3,16));
        album.setPrice(new BigDecimal("25.99"));
    }

    @Test
    void createAlbum_ShouldReturnCreatedAlbum() throws Exception {
        Album album = new Album();
        album.setId(UUID.randomUUID());
        album.setTitle("Test Album");
        album.setArtist("Test Artist");
        album.setReleaseDate(LocalDate.now());
        album.setPrice(BigDecimal.valueOf(20.99));

        when(albumService.createAlbum(album)).thenReturn(album);

        mockMvc.perform(post("/api/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Album\",\"artist\":\"Test Artist\",\"releaseDate\":\"2025-03-16T00:00:00\",\"price\":20.99}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Album"))
                .andExpect(jsonPath("$.artist").value("Test Artist"))
                .andExpect(jsonPath("$.price").value(20.99));
    }

    @Test
    public void updateAlbum_ShouldReturnUpdatedAlbum() throws Exception {
        Album updatedAlbum = new Album();
        updatedAlbum.setId(album.getId());
        updatedAlbum.setTitle("Updated Album");
        updatedAlbum.setArtist("Updated Artist");
        updatedAlbum.setReleaseDate(LocalDate.of(2025, 4, 1));
        updatedAlbum.setPrice(new BigDecimal("30.00"));


        when(albumService.updateAlbum(eq(album.getId()), any(Album.class))).thenReturn(updatedAlbum);

        mockMvc.perform(put("/albums/{albumId}", album.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Updated Album\", \"artist\": \"Updated Artist\", \"releaseDate\": \"2025-04-01\", \"price\": 30.00 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Album"))
                .andExpect(jsonPath("$.artist").value("Updated Artist"))
                .andExpect(jsonPath("$.releaseDate").value("2025-04-01"))
                .andExpect(jsonPath("$.price").value(30.00));
    }


    @Test
    public void deleteAlbum_ShouldReturnNoContent() throws Exception {
        doNothing().when(albumService).deleteAlbum(album.getId());

        mockMvc.perform(delete("/albums/{albumId}", album.getId()))
                .andExpect(status().isNoContent());

        verify(albumService, times(1)).deleteAlbum(album.getId());
    }

    @Test
    public void getAlbumById_ShouldReturnAlbum() throws Exception {
        when(albumService.getAlbumById(album.getId())).thenReturn(album);

        mockMvc.perform(get("/albums/{albumId}", album.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Album 1"));
    }
}

