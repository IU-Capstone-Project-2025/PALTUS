package com.paltus.backend.model.dto;

public record LLMResponseDTO (
    String response,
    String sessionId
){ }
