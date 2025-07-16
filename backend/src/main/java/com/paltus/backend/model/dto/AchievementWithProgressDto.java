package com.paltus.backend.model.dto;

public record AchievementWithProgressDto(
    String name,
    String description,
    int progress,
    boolean completed
) {}
