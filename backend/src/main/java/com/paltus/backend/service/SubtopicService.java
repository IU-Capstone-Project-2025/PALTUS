package com.paltus.backend.service;

import org.springframework.stereotype.Service;

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

    public String getNotes(Long id) {
        return subtopicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No subtopic with id " + id))
                .getNotes();
    }

}
