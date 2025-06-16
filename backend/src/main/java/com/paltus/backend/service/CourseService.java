package com.paltus.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paltus.backend.dto.CourseSummaryDto;
import com.paltus.backend.mapper.CourseMapper;
import com.paltus.backend.model.Course;
import com.paltus.backend.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public Course saveCourse(Course course) {
        course.setupRelationships();
        Course newCourse = courseRepository.save(course);
        return newCourse;
    }

    // TODO: Exception handling
    public Course getCourseById(long course_id) {
        return courseRepository.findById(course_id).get();
    }

    public List<CourseSummaryDto> getAllCoursesSummaries() {
        List<Course> courses = courseRepository.findAll();
        List<CourseSummaryDto> coursesDtos = new ArrayList<>();
        for (Course course: courses) {
            coursesDtos.add(courseMapper.toCourseSummaryDto(course));
        }
        return coursesDtos;
    }

    public void deleteCourse(long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with id " + id);
        }
        courseRepository.deleteById(id);
    }
}
