package com.paltus.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for user login credentials.
 * Contains the user's email and password.
 */
@Schema(name = "LoginUserDto", description = "DTO for user login credentials")
public record LoginUserDto(
    String email,
    String password
) {}
