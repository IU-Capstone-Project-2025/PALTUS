package com.paltus.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.model.dto.LessonDto;
import com.paltus.backend.service.LessonService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/lessons")
public class LessonController {
    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/{id}")
    public LessonDto getLessonById(@PathVariable long id) {
        return this.lessonService.getLessonById(id);
    }

    @PutMapping("passQuiz/{id}")
    public void putMethodName(@PathVariable Long id, @PathVariable double correct) {
        
    }
}
