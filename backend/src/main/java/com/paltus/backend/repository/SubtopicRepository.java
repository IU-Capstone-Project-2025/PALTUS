package com.paltus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paltus.backend.model.Subtopic;

public interface SubtopicRepository extends JpaRepository<Subtopic, Long> {
    @Modifying
    @Query("UPDATE Subtopic s SET s.finished = :state WHERE s.id = :id")
    void updateState(@Param("id") Long id, @Param("state") boolean state);
}
