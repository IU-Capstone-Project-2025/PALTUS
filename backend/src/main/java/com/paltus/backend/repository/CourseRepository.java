package com.paltus.backend.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paltus.backend.model.Course;

import jakarta.transaction.Transactional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Course c SET c.lastActivityTime = :time WHERE c.id = :id")
    void updateLastActivityTime(@Param("id") long id, @Param("time") Instant instant);
}
