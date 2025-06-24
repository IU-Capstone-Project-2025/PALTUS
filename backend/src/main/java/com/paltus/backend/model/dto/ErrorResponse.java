package com.paltus.backend.model.dto;

public record ErrorResponse (
    String timestamp,
    int statusCode,
    String error,
    String message,
    String path
) {}

