package com.paltus.backend.dto;

public record SubtopicDto(
    long id,
    String topic,
    String notes,
    boolean finished
) {}
