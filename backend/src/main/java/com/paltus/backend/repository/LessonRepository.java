package com.paltus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paltus.backend.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    
}
