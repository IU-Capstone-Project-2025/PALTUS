package com.paltus.backend.model.dto;

/**
 * DTO for registering a new user.
 * Contains username, email, and password.
 */
public record RegisterUserDto(
    String username,
    String email,
    String password
) {}
