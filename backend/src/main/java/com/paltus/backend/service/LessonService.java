package com.paltus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
