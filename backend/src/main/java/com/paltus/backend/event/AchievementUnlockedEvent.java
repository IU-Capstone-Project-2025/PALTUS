package com.paltus.backend.event;

import com.paltus.backend.model.Achievement;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Domain event that is published when a user unlocks an achievement.
 */
@AllArgsConstructor
@Getter
public class AchievementUnlockedEvent {
    private long userId;
    private Achievement achievement;
}
