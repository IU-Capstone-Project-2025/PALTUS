package com.paltus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.aspect.annotation.UpdateLastActivityTime;
import com.paltus.backend.dto.CoursePageDto;
import com.paltus.backend.dto.CourseSummaryDto;
import com.paltus.backend.model.Course;
import com.paltus.backend.service.CourseService;

// TODO: change URL 
@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public List<CourseSummaryDto> getAllCoursesSummaries() {
        return courseService.getAllCoursesSummaries();
    }

    @GetMapping("/{id}")
    public CoursePageDto getCourseById(@PathVariable(name = "id") long id) {
        return courseService.getCourseById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/saveCourse")
    @UpdateLastActivityTime
    public Course postMethodName(@RequestBody Course course) {
        courseService.saveCourse(course);
        return course;
    }

}
