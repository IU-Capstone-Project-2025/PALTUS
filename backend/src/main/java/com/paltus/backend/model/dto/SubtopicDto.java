package com.paltus.backend.model.dto;

public record SubtopicDto(
    long id,
    String topic,
    String notes,
    boolean finished
) {}
