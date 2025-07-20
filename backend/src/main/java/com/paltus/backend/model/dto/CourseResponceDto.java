package com.paltus.backend.model.dto;

import com.paltus.backend.model.Course;

/**
 * DTO containing the generated course and the associated session ID.
 */
public record CourseResponceDto(
    Course course,
    String sessionId
){ }
