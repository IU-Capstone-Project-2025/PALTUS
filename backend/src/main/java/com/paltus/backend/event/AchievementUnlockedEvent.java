package com.paltus.backend.event;

import com.paltus.backend.model.Achievement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AchievementUnlockedEvent {
    private long userId;
    private Achievement achievement;
}
