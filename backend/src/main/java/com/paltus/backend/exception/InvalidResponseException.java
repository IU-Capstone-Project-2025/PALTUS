package com.paltus.backend.exception;

public class InvalidResponseException extends RuntimeException{
    public InvalidResponseException(String message) {
        super(message);
    }
}
