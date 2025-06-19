package com.paltus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paltus.backend.model.Subtopic;

public interface SubtopicRepository extends JpaRepository<Subtopic, Long> {
    
}
