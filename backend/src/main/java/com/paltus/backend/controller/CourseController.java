package com.paltus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.model.Course;
import com.paltus.backend.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable(name = "id") int id) {
        return courseService.getCourseById(id);
    }

}
