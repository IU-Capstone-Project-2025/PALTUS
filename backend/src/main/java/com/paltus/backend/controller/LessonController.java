package com.paltus.backend.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.service.LessonService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Operation(summary = "Mark quiz as passed", description = "Mark the quiz as passed for the lesson with the given ID")
    @PutMapping("/passQuiz/{id}")
    public void markQuizAsPassed(@PathVariable Long id) {
        lessonService.setQuizAsPassed(id);
    }
}
