package com.paltus.backend.service;

import org.springframework.stereotype.Service;

import com.paltus.backend.model.Subtopic;
import com.paltus.backend.model.dto.SubtopicContextDto;
import com.paltus.backend.repository.SubtopicRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class SubtopicService {
    private final SubtopicRepository subtopicRepository;

    public SubtopicService(SubtopicRepository subtopicRepository) {
        this.subtopicRepository = subtopicRepository;
    }

    public void setFinishedState(Long id, boolean state) {
        if (!subtopicRepository.existsById(id)) {
            throw new EntityNotFoundException("No subtopic with id " + id);
        }
        subtopicRepository.updateState(id, state);
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
