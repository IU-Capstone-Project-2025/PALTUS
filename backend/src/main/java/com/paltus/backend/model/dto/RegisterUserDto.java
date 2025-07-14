package com.paltus.backend.model.dto;

public record RegisterUserDto(
    String username,
    String email,
    String password
) {}
