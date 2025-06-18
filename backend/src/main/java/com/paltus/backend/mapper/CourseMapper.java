package com.paltus.backend.mapper;

import com.paltus.backend.dto.CoursePageDto;
import com.paltus.backend.dto.CourseSummaryDto;
import com.paltus.backend.dto.LessonDto;
import com.paltus.backend.dto.SubtopicDto;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.Lesson;
import com.paltus.backend.model.Subtopic;

public interface CourseMapper {
    CourseSummaryDto toCourseSummaryDto(Course course);

    CoursePageDto toCoursePageDto(Course course);

    LessonDto toLessonDto(Lesson lesson);

    SubtopicDto toSubtopicDto(Subtopic subtopic);
}
