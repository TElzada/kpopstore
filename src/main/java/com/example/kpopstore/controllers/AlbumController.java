package com.example.kpopstore.controllers;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.exceptions.AlbumNotFoundException;
import com.example.kpopstore.exceptions.InvalidAlbumException;
import com.example.kpopstore.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) {
        try {
            Album createdAlbum = albumService.createAlbum(album);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAlbum);
        } catch (InvalidAlbumException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<Album> updateAlbum(@PathVariable UUID albumId, @RequestBody Album album) {
        try {
            Album updatedAlbum = albumService.updateAlbum(albumId, album);
            return ResponseEntity.ok(updatedAlbum);
        } catch (AlbumNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (InvalidAlbumException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
