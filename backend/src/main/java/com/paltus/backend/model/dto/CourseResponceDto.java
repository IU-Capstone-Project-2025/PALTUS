package com.paltus.backend.model.dto;

import com.paltus.backend.model.Course;

public record CourseResponceDto(
    Course course,
    String sessionId
){ }
