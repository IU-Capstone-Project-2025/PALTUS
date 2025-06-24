package com.paltus.backend.exception;

public class InvalidPromtInputException extends RuntimeException{
    public InvalidPromtInputException(String message) {
        super(message);
    }
}
