package com.paltus.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for verifying a user's account with email and verification code.
 */
@Schema(name = "VerifyUserDto", description = "DTO for verifying user account with email and verification code")
public record VerifyUserDto(
    String email,
    String verificationCode
) {}
