package com.paltus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paltus.backend.model.Achievement;
import com.paltus.backend.model.enums.AchievementType;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    List<Achievement> findAllByType(AchievementType type);
}
