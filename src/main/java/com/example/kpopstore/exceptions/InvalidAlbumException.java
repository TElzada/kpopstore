package com.example.kpopstore.exceptions;

public class InvalidAlbumException extends RuntimeException {

    public InvalidAlbumException(String message) {
        super(message);
    }

    public InvalidAlbumException(String message, Throwable cause) {
        super(message, cause);
    }
}

