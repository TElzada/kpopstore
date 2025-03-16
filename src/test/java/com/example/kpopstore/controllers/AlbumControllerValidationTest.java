package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.services.AlbumService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AlbumController.class)
public class AlbumControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AlbumService albumService;

    @InjectMocks
    private AlbumController albumController;

    @Test
    public void shouldReturn400WhenAlbumValidationFails() throws Exception {
        Album invalidAlbum = new Album("", "", "", null, null);

        when(albumService.createAlbum(invalidAlbum)).thenReturn(invalidAlbum);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"artist\":\"\",\"releaseDate\":\"\",\"price\":null}"))
                .andExpect(status().isBadRequest())  // Ожидаем статус 400
                .andExpect(jsonPath("$.validationErrors.title").value("Title is required"))
                .andExpect(jsonPath("$.validationErrors.artist").value("Artist is required"))
                .andExpect(jsonPath("$.validationErrors.releaseDate").value("Release date is required"))
                .andExpect(jsonPath("$.validationErrors.price").value("Price is required"));
    }
}
