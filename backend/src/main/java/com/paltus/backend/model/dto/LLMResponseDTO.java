package com.paltus.backend.model.dto;

/**
 * DTO representing a response from the LLM including generated text and session ID.
 */
public record LLMResponseDTO (
    String response,
    String sessionId
){}
