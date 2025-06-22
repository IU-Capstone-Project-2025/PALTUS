package com.paltus.backend.mapper;

import com.paltus.backend.model.Course;
import com.paltus.backend.model.Lesson;
import com.paltus.backend.model.Subtopic;
import com.paltus.backend.model.dto.CoursePageDto;
import com.paltus.backend.model.dto.CourseSummaryDto;
import com.paltus.backend.model.dto.LessonDto;
import com.paltus.backend.model.dto.NextLessonDto;
import com.paltus.backend.model.dto.SubtopicDto;

public interface CourseMapper {
    CourseSummaryDto toCourseSummaryDto(Course course, int lastLesson);

    CoursePageDto toCoursePageDto(Course course);

    LessonDto toLessonDto(Lesson lesson);

    SubtopicDto toSubtopicDto(Subtopic subtopic);

    NextLessonDto toNextLessonDto(Lesson lesson, Course course);
}
