package com.paltus.backend.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.paltus.backend.model.Achievement;
import com.paltus.backend.model.User;
import com.paltus.backend.repository.UserRepository;

@Component
public class AchievementUnlockedListener {
    private final double MULTIPLIER = 1.25;

    private final UserRepository userRepository;

    public AchievementUnlockedListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void handleAchievementUnlocked(AchievementUnlockedEvent event) {
        User user = userRepository.findById(event.getUserId()).get();
        Achievement achievement = event.getAchievement();

        int totalExp = user.getCurrentExp() + achievement.getExperience();
        if (totalExp >= user.getRequiredExp()) {
            user.setCurrentExp(totalExp - user.getRequiredExp());
            user.setLevel(user.getLevel() + 1);
            user.setRequiredExp(getNextRequiredExp(user.getRequiredExp()));
        } else {
            user.setCurrentExp(totalExp);
        }
        userRepository.save(user);
    }

    public int getNextRequiredExp(int currentRequiredExp) {
        return (int)(currentRequiredExp * MULTIPLIER / 10) * 10;
    }
}
