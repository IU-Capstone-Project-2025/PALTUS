package com.paltus.backend.service;

import org.springframework.stereotype.Service;

import com.paltus.backend.model.Subtopic;
import com.paltus.backend.model.dto.SubtopicContextDto;
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
        lessonService.handleSubtopicFinished(subtopicRepository.getLessonIdById(id));
    }

    public void setNotes(Long id, String note) {
        if (!subtopicRepository.existsById(id)) {
            throw new EntityNotFoundException("No subtopic with id " + id);
        }
        subtopicRepository.updateNotes(id, note);
    }

    public void addNotes(Long id, String newNote) {
        Subtopic subtopic = subtopicRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("No subtopic with id " + id));
    
        String existingNotes = subtopic.getNotes();
        if (existingNotes == null) {
            existingNotes = "";
        }
    
        String updatedNotes = existingNotes + "\n" + newNote;
        subtopicRepository.updateNotes(id, updatedNotes);
    }

    public String getNotes(Long id) {
        return subtopicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No subtopic with id " + id))
                .getNotes();
    }

    /**
     * Get detailed context of the subtopic including
     * course name, lesson title, topic, and notes.
     */
    public SubtopicContextDto getContext(Long id) {
        Subtopic subtopic = subtopicRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No subtopic with id " + id));
        String courseName = subtopic.getLesson().getCourse().getCourse_name();
        String lessonTitle = subtopic.getLesson().getTitle();
        String subtopicTopic = subtopic.getTopic();
        String notes = subtopic.getNotes();
        return new SubtopicContextDto(courseName, lessonTitle, subtopicTopic, notes);
    }

}
