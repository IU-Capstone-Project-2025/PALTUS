package com.paltus.backend.controller;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.model.Course;
import com.paltus.backend.service.ChatService;
import com.paltus.backend.service.CourseService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class GptController {
    private final ChatService chatService;
    private final CourseService courseService;
    public GptController(ChatService chatService, CourseService courseService) {
        this.chatService = chatService;
        this.courseService = courseService;
    }
    @GetMapping()
    public Course getSomething() {
        // return chatService.createCourse();
        Course course = chatService.createCourse();
        return courseService.saveCourse(course);
    }
    
}
