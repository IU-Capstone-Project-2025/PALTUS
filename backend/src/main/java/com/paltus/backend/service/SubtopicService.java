package com.paltus.backend.service;

import org.springframework.stereotype.Service;

import com.paltus.backend.repository.SubtopicRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class SubtopicService {
    private final SubtopicRepository subtopicRepository;
    private final LessonService lessonService;
    private final UserService userService;

    public SubtopicService(SubtopicRepository subtopicRepository, LessonService lessonService, UserService userService) {
        this.subtopicRepository = subtopicRepository;
        this.lessonService = lessonService;
        this.userService = userService;
    }

    public void setFinishedState(Long id, boolean state) {
        if (!subtopicRepository.existsById(id)) {
            throw new EntityNotFoundException("No subtopic with id " + id);
        }
        subtopicRepository.updateState(id, state);
        
        userService.updateStreak();
    }

    public void setNotes(Long id, String note) {
        if (!subtopicRepository.existsById(id)) {
            throw new EntityNotFoundException("No subtopic with id " + id);
        }
        subtopicRepository.updateNotes(id, note);
    }

    public String getNotes(Long id) {
        return subtopicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No subtopic with id " + id))
                .getNotes();
    }

}
