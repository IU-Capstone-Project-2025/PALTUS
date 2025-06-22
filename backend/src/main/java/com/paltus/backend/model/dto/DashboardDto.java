package com.paltus.backend.model.dto;
import java.util.List;

public record DashboardDto(
    List<CourseSummaryDto> courses,
    NextLessonDto nextLesson
){}
