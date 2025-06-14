package com.paltus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paltus.backend.model.Course;
import com.paltus.backend.repository.CourseRepository;

@Service
public class CourseService {
    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course saveCourse(Course course) {
        course.setupRelationships();
        Course newCourse = courseRepository.save(course);
        return newCourse;
    }
}
