package com.paltus.backend.model.dto;

public record VerifyUserDto(
    String email,
    String verificationCode
) {}
