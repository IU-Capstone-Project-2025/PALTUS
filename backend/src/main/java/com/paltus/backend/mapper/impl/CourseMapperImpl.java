package com.paltus.backend.mapper.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.paltus.backend.mapper.CourseMapper;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.Lesson;
import com.paltus.backend.model.Subtopic;
import com.paltus.backend.model.dto.CoursePageDto;
import com.paltus.backend.model.dto.CourseSummaryDto;
import com.paltus.backend.model.dto.DashboardDto;
import com.paltus.backend.model.dto.LessonDto;
import com.paltus.backend.model.dto.NextLessonDto;
import com.paltus.backend.model.dto.SubtopicDto;

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
    public LessonDto toLessonDto(Lesson lessonWithSubtopicsAndLinks) {

        return new LessonDto(lessonWithSubtopicsAndLinks.getId(),
                lessonWithSubtopicsAndLinks.getLesson_number(),
                lessonWithSubtopicsAndLinks.getTitle(),
                lessonWithSubtopicsAndLinks.isQuiz(),
                lessonWithSubtopicsAndLinks.getLinks(),
                lessonWithSubtopicsAndLinks.getSubtopics().stream().map(subtopic -> this.toSubtopicDto(subtopic))
                        .toList());
    }

    @Override
    public SubtopicDto toSubtopicDto(Subtopic subtopic) {
        return new SubtopicDto(subtopic.getTopic(), subtopic.isFinished());
    }

    @Override
    public NextLessonDto toNextLessonDto(Lesson lesson, Course course) {
        return new NextLessonDto(course.getId(), lesson.getTitle(), lesson.getSubtopics().stream().map(subtopic -> this.toSubtopicDto(subtopic))
        .toList());
    }

    @Override
    public DashboardDto toDashboardDto(List<CourseSummaryDto> courses, Lesson nextLesson) {
        if (nextLesson == null) {
            return new DashboardDto(courses, null);
        }
        return new DashboardDto(courses, toNextLessonDto(nextLesson, nextLesson.getCourse()));
    }

}
