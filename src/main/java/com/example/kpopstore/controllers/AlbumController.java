package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.exceptions.AlbumNotFoundException;
import com.example.kpopstore.exceptions.InvalidAlbumException;
import com.example.kpopstore.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<Album> getAlbumById(@PathVariable UUID albumId) {
        try {
            Album album = albumService.getAlbumById(albumId);
            return ResponseEntity.ok(album);
        } catch (AlbumNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    @PostMapping
    public ResponseEntity<?> createAlbum(@Valid @RequestBody Album album, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessages.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages.toString());
        }

        try {
            Album createdAlbum = albumService.createAlbum(album);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAlbum);
        } catch (InvalidAlbumException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid album data");
        }
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<?> updateAlbum(@PathVariable UUID albumId, @Valid @RequestBody Album album, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessages.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages.toString());
        }

        try {
            Album updatedAlbum = albumService.updateAlbum(albumId, album);
            return ResponseEntity.ok(updatedAlbum);
        } catch (AlbumNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidAlbumException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid album data");
        }
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable UUID albumId) {
        try {
            albumService.deleteAlbum(albumId);
            return ResponseEntity.noContent().build();
        } catch (AlbumNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
