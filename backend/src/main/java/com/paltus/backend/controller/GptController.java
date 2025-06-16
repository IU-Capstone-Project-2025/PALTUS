package com.paltus.backend.controller;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.model.Course;
import com.paltus.backend.model.CourseRequest;
import com.paltus.backend.service.ChatService;
import com.paltus.backend.service.CourseService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class GptController {
    private final ChatService chatService;
    private final CourseService courseService;
    public GptController(ChatService chatService, CourseService courseService) {
        this.chatService = chatService;
        this.courseService = courseService;
    }
    @PostMapping("/createCourse")
    public Course updateStudent( @RequestBody CourseRequest courseRequest) {
        return chatService.createCourse(courseRequest);
    }
    @GetMapping()
    public Course getSomething() {
        // return chatService.createCourse();
        Course course = chatService.createCourse();
        return courseService.saveCourse(course);
    }
    
}
