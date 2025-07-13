package com.paltus.backend.service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.paltus.backend.model.dto.LessonContextDto;
import com.paltus.backend.model.dto.LessonDto;
import com.paltus.backend.mapper.CourseMapper;
import com.paltus.backend.model.Lesson;
import com.paltus.backend.repository.LessonRepository;

@Service
public class LessonService {
    private LessonRepository lessonRepository;
    private CourseMapper courseMapper;

    public LessonService(LessonRepository lessonRepository, CourseMapper courseMapper) {
        this.lessonRepository = lessonRepository;
        this.courseMapper = courseMapper;
    }

    public LessonDto getLessonById(long lesson_id) {
        Lesson lesson = this.lessonRepository.findById(lesson_id).get();
        return this.courseMapper.toLessonDto(lesson);
    }

    public LessonContextDto getLessonContext(Long id) {
        Lesson lesson = this.lessonRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + id));

        String courseName = lesson.getCourse().getCourse_name();
        String lessonName = lesson.getTitle();

        return new LessonContextDto(
        courseName,
        lessonName,
        lesson.getSubtopics()
            .stream()
            .map(courseMapper::toSubtopicDto)
            .collect(Collectors.toCollection(ArrayList::new))
        );
    }
}
