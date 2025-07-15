package com.paltus.backend.model.dto;

import java.util.ArrayList;

public record LessonContextDto(
    String courseName,
    String lessonName,
    ArrayList<SubtopicDto> subtopics
) { }
