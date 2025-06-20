package com.paltus.backend.service;

import org.springframework.stereotype.Service;

import com.paltus.backend.repository.SubtopicRepository;

import jakarta.transaction.Transactional;

@Service
public class SubtopicService {
    private final SubtopicRepository subtopicRepository;

    public SubtopicService(SubtopicRepository subtopicRepository) {
        this.subtopicRepository = subtopicRepository;
    }

    @Transactional
    public void setFinishedState(Long id, boolean state) {
        subtopicRepository.updateState(id, state);
    }

}
