package com.paltus.backend.mapper.impl;

import org.springframework.stereotype.Component;

import com.paltus.backend.dto.CoursePageDto;
import com.paltus.backend.dto.CourseSummaryDto;
import com.paltus.backend.dto.LessonDto;
import com.paltus.backend.dto.SubtopicDto;
import com.paltus.backend.mapper.CourseMapper;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.Lesson;
import com.paltus.backend.model.Subtopic;

@Component
public class CourseMapperImpl implements CourseMapper {
    @Override
    public CourseSummaryDto toCourseSummaryDto(Course course) {
        return new CourseSummaryDto(course.getId(), course.getCourse_name());
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
        return new SubtopicDto(subtopic.getId(),
                subtopic.getTopic(),
                subtopic.getNotes(),
                subtopic.isFinished());
    }
}
