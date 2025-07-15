package com.paltus.backend.model.dto;

import java.util.List;

public record UserGameStatsDto(
    int streak,
    int level,
    int currentExp,
    int requiredExp,
    String title,
    List<AchievementWithProgressDto> achievements
) {}
