package com.paltus.backend.model.dto;

public record CourseSummaryDto(
    Long id,
    String course_name,
    int lastLesson
) {}
