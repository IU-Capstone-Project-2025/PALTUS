package com.paltus.backend.model.dto;

import java.util.List;

public record LessonDto (
    long id,
    int lesson_number,
    String title,
    boolean quiz,
    List<String> links,
    List<SubtopicDto> subtopics
) {}
