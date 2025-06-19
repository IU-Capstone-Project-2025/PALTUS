package com.paltus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.dto.LessonDto;
import com.paltus.backend.model.requests.SubtopicSetStateRequest;
import com.paltus.backend.service.LessonService;
import com.paltus.backend.service.SubtopicService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/lessons")
public class LessonController {
    private LessonService lessonService;
    private SubtopicService subtopicService;

    @Autowired
    public LessonController(LessonService lessonService, SubtopicService subtopicService) {
        this.lessonService = lessonService;
        this.subtopicService = subtopicService;
    }

    @GetMapping("/{id}")
    public LessonDto getLessonById(@PathVariable long id) {
        return this.lessonService.getLessonById(id);
    }

    @PutMapping("subtopic/{id}")
    public void putMethodName(@PathVariable Long id, @RequestBody SubtopicSetStateRequest request) {
        System.out.println(request.isState());
        subtopicService.setFinishedState(id, request.isState());
    }
}
