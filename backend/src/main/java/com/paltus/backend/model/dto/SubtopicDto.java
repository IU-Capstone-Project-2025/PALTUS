package com.paltus.backend.model.dto;

public record SubtopicDto(
    String topicName,
    boolean finished,
    String notes
) {}
