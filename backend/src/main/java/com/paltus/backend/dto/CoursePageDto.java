package com.paltus.backend.dto;

import java.util.List;

public record CoursePageDto(
    long id,
    String course_name,
    String description,
    List<String> books,
    List<LessonDto> lessons
) {}
