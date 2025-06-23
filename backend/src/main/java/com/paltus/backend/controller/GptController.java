package com.paltus.backend.controller;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.model.Course;
import com.paltus.backend.model.requests.CourseRequest;
import com.paltus.backend.service.ChatService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class GptController {
    private final ChatService chatService;
    public GptController(ChatService chatService) {
        this.chatService = chatService;
    }
    @PostMapping("/createCourse")
    public Course updateStudent( @RequestBody CourseRequest courseRequest) {
        Course course = chatService.createCourse(courseRequest);
        return course;
    }
    @GetMapping()
    public String getSomething() {
        return "Something on the way";
    }
    
}
