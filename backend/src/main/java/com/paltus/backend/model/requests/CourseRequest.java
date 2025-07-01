package com.paltus.backend.model.requests;

import lombok.Data;

@Data
public class CourseRequest {
    String courseName;
    String goal;
    String knowledge;
    int lessons;
    String time;
}
