package com.paltus.backend.service;

import org.springframework.stereotype.Service;

import com.paltus.backend.model.Subtopic;
import com.paltus.backend.repository.SubtopicRepository;

@Service
public class SubtopicService {
    private final SubtopicRepository subtopicRepository;

    public SubtopicService(SubtopicRepository subtopicRepository) {
        this.subtopicRepository = subtopicRepository;
    }

    public void setFinishedState(Long id, boolean state) {
        Subtopic subtopic = subtopicRepository.findById(id).get();
        subtopic.setFinished(state);
        subtopicRepository.save(subtopic);
    }

}
