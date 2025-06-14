package com.paltus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paltus.backend.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    
}
