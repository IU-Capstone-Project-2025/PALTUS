package com.paltus.backend.model.dto;

import java.util.List;

public record NextLessonDto(
    Long courseId,
    String lessonTitle,
    List<SubtopicForNextLessonDto> subtopics
){}
