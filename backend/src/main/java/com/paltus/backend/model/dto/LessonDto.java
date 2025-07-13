package com.paltus.backend.model.dto;

import java.util.List;

import com.paltus.backend.model.Subtopic;

public record LessonDto (
    long id,
    int lesson_number,
    String title,
    boolean quiz,
    List<Subtopic> subtopics
) {}
