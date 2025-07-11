package com.paltus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paltus.backend.model.Lesson;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("""
    SELECT DISTINCT l FROM Lesson l
    JOIN l.subtopics s
    WHERE s.finished = false AND l.course.id = :courseId
            """)
    List<Lesson> findUnfinishedLessonsByCourseId(@Param("courseId") Long courseId);
}
