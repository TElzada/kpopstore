package com.example.kpopstore.services;

import com.example.kpopstore.entities.Album;
import com.example.kpopstore.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }


    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }


    public Album updateAlbum(UUID albumId, Album updatedAlbum) {
        Optional<Album> existingAlbumOpt = albumRepository.findById(albumId);
        if (existingAlbumOpt.isPresent()) {
            Album existingAlbum = existingAlbumOpt.get();
            existingAlbum.setTitle(updatedAlbum.getTitle());
            existingAlbum.setArtist(updatedAlbum.getArtist());
            existingAlbum.setReleaseDate(updatedAlbum.getReleaseDate());
            existingAlbum.setPrice(updatedAlbum.getPrice());
            existingAlbum.setStock(updatedAlbum.getStock());
            existingAlbum.setDescription(updatedAlbum.getDescription());
            existingAlbum.setCoverImage(updatedAlbum.getCoverImage());
            return albumRepository.save(existingAlbum);
        }
        return null;
    }


    public Album getAlbumById(UUID albumId) {
        Optional<Album> album = albumRepository.findById(albumId);
        return album.orElse(null);
    }


    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }


    @Transactional
    public boolean deleteAlbum(UUID albumId) {
        Optional<Album> album = albumRepository.findById(albumId);
        if (album.isPresent()) {
            albumRepository.delete(album.get());
            return true;
        }
        return false;
    }


    public List<Album> findAlbumsByTitle(String title) {
        return albumRepository.findAll().stream()
                .filter(album -> album.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }


    public boolean isAlbumInStock(UUID albumId, int quantity) {
        Optional<Album> albumOpt = albumRepository.findById(albumId);
        return albumOpt.isPresent() && albumOpt.get().getStock() >= quantity;
    }
}

