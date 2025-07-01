package com.paltus.backend.model.requests;

import com.paltus.backend.model.Course;

import lombok.Data;

@Data
public class SaveCourseRequest {
    Course course;
    String sessionId;
}
