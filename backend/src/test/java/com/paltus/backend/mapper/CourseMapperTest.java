package com.paltus.backend.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.paltus.backend.model.dto.CoursePageDto;
import com.paltus.backend.model.dto.CourseSummaryDto;
import com.paltus.backend.model.dto.LessonDto;
import com.paltus.backend.model.dto.SubtopicDto;
import com.paltus.backend.mapper.impl.CourseMapperImpl;
import com.paltus.backend.model.Course;
import com.paltus.backend.model.Lesson;
import com.paltus.backend.model.Subtopic;

public class CourseMapperTest {
    private final CourseMapper mapper = new CourseMapperImpl();

    @Test
    void toCourseSummaryDto_MapsCorrectly() {
        Course course = new Course();
        course.setId(1);
        course.setCourse_name("Test Course");

        CourseSummaryDto dto = mapper.toCourseSummaryDto(course, 1);

        assertEquals(1, dto.id());
        assertEquals("Test Course", dto.course_name());
        assertEquals(1, dto.nextLesson());
    }

    @Test
    void toCoursePageDto_MapsAllFields() {
        Course course = new Course();
        course.setId(1);
        course.setCourse_name("Test Course");
        course.setDescription("Course Description");
        course.setBooks(List.of("Book 1", "Book 2"));
        
        Lesson lesson = new Lesson();
        lesson.setId(10);
        lesson.setLesson_number(1);
        lesson.setTitle("Lesson 1");
        lesson.setQuiz(false);
        lesson.setLinks(List.of("link1", "link2"));
        lesson.setSubtopics(Collections.emptyList());
        
        course.setLessons(List.of(lesson));

        CoursePageDto dto = mapper.toCoursePageDto(course);

        assertEquals(1, dto.id());
        assertEquals("Test Course", dto.course_name());
        assertEquals("Course Description", dto.description());
        assertEquals(List.of("Book 1", "Book 2"), dto.books());
        assertEquals(1, dto.lessons().size());
        assertEquals(10, dto.lessons().get(0).id());
    }

    @Test
    void toLessonDto_MapsSubtopicsCorrectly() {
        Lesson lesson = new Lesson();
        lesson.setId(1);
        lesson.setLesson_number(1);
        lesson.setTitle("Introduction");
        lesson.setQuiz(true);
        lesson.setLinks(List.of("https://example.com"));
        
        Subtopic subtopic = new Subtopic();
        subtopic.setId(100);
        subtopic.setTopic("Subtopic 1");
        subtopic.setNotes("Notes");
        subtopic.setFinished(false);
        
        lesson.setSubtopics(List.of(subtopic));

        LessonDto dto = mapper.toLessonDto(lesson);
        
        assertEquals(1, dto.id());
        assertEquals(1, dto.lesson_number());
        assertEquals("Introduction", dto.title());
        assertTrue(dto.quiz());
        assertEquals(List.of("https://example.com"), dto.links());
        assertEquals(1, dto.subtopics().size());
        assertEquals("Subtopic 1", dto.subtopics().get(0).getTopic());
    }

    @Test
    void toSubtopicDto_MapsCorrectly() {
        Subtopic subtopic = new Subtopic();
        subtopic.setTopic("Data Structures");
        subtopic.setFinished(true);
        subtopic.setNotes("Important notes");
        
        SubtopicDto dto = mapper.toSubtopicDto(subtopic);
        
        assertEquals("Data Structures", dto.topicName());
        assertTrue(dto.finished());
        assertEquals("Important notes", dto.notes());
    }
}