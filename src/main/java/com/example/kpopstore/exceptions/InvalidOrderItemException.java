package com.example.kpopstore.exceptions;

public class InvalidOrderItemException extends RuntimeException {

    public InvalidOrderItemException(String message) {
        super(message);
    }

    public InvalidOrderItemException(String message, Throwable cause) {
        super(message, cause);
    }
}

