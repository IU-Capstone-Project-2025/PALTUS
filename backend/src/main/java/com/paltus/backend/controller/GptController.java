package com.paltus.backend.controller;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.model.dto.CourseResponceDto;
import com.paltus.backend.model.dto.LLMResponseDTO;
import com.paltus.backend.model.dto.QuizDto;
import com.paltus.backend.model.requests.CourseRequest;
import com.paltus.backend.model.requests.EditCourseRequest;
import com.paltus.backend.model.requests.GenerateContentRequest;
import com.paltus.backend.service.ChatService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class GptController {
    private final ChatService chatService;
    public GptController(ChatService chatService) {
        this.chatService = chatService;
    }
    @PostMapping("/createCourse")
    public CourseResponceDto createCourse( @RequestBody CourseRequest courseRequest) {
        return chatService.generateInitialCourse(courseRequest);
    }
    @PostMapping("/editCourse")
    public CourseResponceDto postMethodName(@RequestBody EditCourseRequest request) {
        return chatService.editCourse(request);
    }
    @PostMapping("/subtopicAskLLM/{id}")
    public LLMResponseDTO postMethodName(@RequestBody GenerateContentRequest request, @PathVariable Long id) {
        return chatService.getContent(request, id);
    }
    
    @GetMapping()
    public String getSomething() {
        return "Something on the way";
    }
    
    @GetMapping("/quiz/{id}")
    public QuizDto getMethodName(@PathVariable long id) {
        return chatService.generateQuiz(id);
    }
}
