package com.paltus.backend.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.aspect.annotation.UpdateLastActivityTime;
import com.paltus.backend.model.requests.SubtopicSetStateRequest;
import com.paltus.backend.service.SubtopicService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("lessons/{lesson_id}/subtopics")
public class SubtopicController {
    private final SubtopicService subtopicService;

    public SubtopicController(SubtopicService subtopicService) {
        this.subtopicService = subtopicService;
    }

    @Operation(description = "Set the finished state of the subtopic with the id")
    @PutMapping("setFinished/{id}")
    @UpdateLastActivityTime
    public void setFinishedState(@PathVariable Long id, @RequestBody SubtopicSetStateRequest request) {
        subtopicService.setFinishedState(id, request.isState());
    }

    @Operation(description = "Replaces the notes for the specified subtopic.")
    @PutMapping("setNotes/{id}")
    public void setNotes(@PathVariable Long id, @RequestBody String notes) {
        subtopicService.setNotes(id, notes);
    }

    @Operation(description = "Appends new notes to the existing notes of the specified subtopic.")
    @PutMapping("addNotes/{id}")
    public void addNotes(@PathVariable Long id, @RequestBody String notes) {
        subtopicService.addNotes(id, notes);
    }
}
