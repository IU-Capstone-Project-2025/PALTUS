package com.paltus.backend.mapper;

import java.util.List;

import com.paltus.backend.model.Course;
import com.paltus.backend.model.Lesson;
import com.paltus.backend.model.Subtopic;
import com.paltus.backend.model.dto.CoursePageDto;
import com.paltus.backend.model.dto.CourseResponceDto;
import com.paltus.backend.model.dto.CourseSummaryDto;
import com.paltus.backend.model.dto.DashboardDto;
import com.paltus.backend.model.dto.LessonDto;
import com.paltus.backend.model.dto.NextLessonDto;
import com.paltus.backend.model.dto.SubtopicDto;
import com.paltus.backend.model.dto.SubtopicForNextLessonDto;

public interface CourseMapper {
    CourseSummaryDto toCourseSummaryDto(Course course, int nextLesson);

    /**
     * Converts a Course entity to a detailed CoursePageDto including lessons.
     */
    CoursePageDto toCoursePageDto(Course course);

    LessonDto toLessonDto(Lesson lesson);

    SubtopicDto toSubtopicDto(Subtopic subtopic);

    NextLessonDto toNextLessonDto(Lesson lesson, Course course);

    /**
     * Maps list of course summaries and optional next lesson to DashboardDto.
     * If nextLesson is null, DashboardDto will have no next lesson.
     */
    DashboardDto toDashboardDto(List<CourseSummaryDto> courses, Lesson nextLesson);

    SubtopicForNextLessonDto toSubtopicForNextLessonDto(Subtopic subtopic);

    CourseResponceDto toCourseResponceDto(Course course, String sessionId);
}
