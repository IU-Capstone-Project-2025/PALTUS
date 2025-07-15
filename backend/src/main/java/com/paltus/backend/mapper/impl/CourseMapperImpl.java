package com.paltus.backend.mapper.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.paltus.backend.mapper.CourseMapper;
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

@Component
public class CourseMapperImpl implements CourseMapper {
    @Override
    public CourseSummaryDto toCourseSummaryDto(Course course, int nextLesson) {
        return new CourseSummaryDto(course.getId(), course.getCourse_name(), nextLesson);
    }

    @Override
    public CoursePageDto toCoursePageDto(Course course) {
        return new CoursePageDto(course.getId(),
                course.getCourse_name(),
                course.getDescription(),
                course.getBooks(),
                course.getLessons().stream()
                        .map(lesson -> this.toLessonDto(lesson)).toList());
    }

    @Override
    public LessonDto toLessonDto(Lesson lesson) {

        return new LessonDto(lesson.getId(),
            lesson.getLesson_number(),
            lesson.getTitle(),
            lesson.isQuiz(),
            lesson.isFinished(),
            lesson.getSubtopics());
    }

    @Override
    public SubtopicDto toSubtopicDto(Subtopic subtopic) {
        return new SubtopicDto(subtopic.getTopic(), subtopic.isFinished(), subtopic.getNotes());
    }

    @Override
    public SubtopicForNextLessonDto toSubtopicForNextLessonDto(Subtopic subtopic) {
        return new SubtopicForNextLessonDto(subtopic.getTopic());
    }

    @Override
    public NextLessonDto toNextLessonDto(Lesson lesson, Course course) {
        return new NextLessonDto(course.getId(), lesson.getTitle(), lesson.getSubtopics().stream().map(subtopic -> this.toSubtopicForNextLessonDto(subtopic))
        .toList());
    }

    @Override
    public DashboardDto toDashboardDto(List<CourseSummaryDto> courses, Lesson nextLesson) {
        if (nextLesson == null) {
            return new DashboardDto(courses, null);
        }
        return new DashboardDto(courses, toNextLessonDto(nextLesson, nextLesson.getCourse()));
    }

    @Override
    public CourseResponceDto toCourseResponceDto(Course course, String sessionId) {
        return new CourseResponceDto(course, sessionId);
    }

}
