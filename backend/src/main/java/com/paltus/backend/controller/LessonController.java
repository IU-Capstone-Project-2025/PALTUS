package com.paltus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.dto.LessonDto;
import com.paltus.backend.service.LessonService;

@RestController
@RequestMapping("/api/v1/lessons")
public class LessonController {
    private LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/{id}")
    public LessonDto getLessonById(@PathVariable long id) {
        return this.lessonService.getLessonById(id);
    }
}
