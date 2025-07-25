package com.paltus.backend.event;

import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.paltus.backend.model.Title;
import com.paltus.backend.model.User;
import com.paltus.backend.repository.TitleRepository;
import com.paltus.backend.repository.UserRepository;

@Component
public class AchievementUnlockedListener {
    private final double MULTIPLIER = 1.25;

    private final UserRepository userRepository;
    private final TitleRepository titleRepository;

    public AchievementUnlockedListener(UserRepository userRepository, TitleRepository titleRepository) {
        this.userRepository = userRepository;
        this.titleRepository = titleRepository;
    }

    /**
     * Handles achievement unlocked events by adding experience to the user.
     */
    @EventListener
    public void handleAchievementUnlocked(AchievementUnlockedEvent event) {
        updateLevel(event.getUserId(), event.getAchievement().getExperience());
    }

    /**
     * Handles experience gained events by adding experience to the user.
     */
    @EventListener
    public void handleExpGot(ExpGotEvent event) {
        updateLevel(event.getUserId(), event.getExp());
    }

    /**
     * Updates user's level and experience based on the gained experience points.
     * If the experience threshold is reached, levels up the user and updates the title.
     */
    public void updateLevel(long userId, int exp) {
        User user = userRepository.findById(userId).get();

        int totalExp = user.getCurrentExp() + exp;
        if (totalExp >= user.getRequiredExp()) {
            user.setCurrentExp(totalExp - user.getRequiredExp());
            int currentLevel = user.getLevel() + 1;
            user.setLevel(currentLevel);
            updateTitle(user, currentLevel);
            user.setRequiredExp(getNextRequiredExp(user.getRequiredExp()));
        } else {
            user.setCurrentExp(totalExp);
        }
        userRepository.save(user);
    }

    /**
     * Calculates the next experience required for leveling up.
     */
    public int getNextRequiredExp(int currentRequiredExp) {
        return (int)(currentRequiredExp * MULTIPLIER / 10) * 10;
    }

    /**
     * Updates the user's title based on the current level.
     */
    public void updateTitle(User user, int currentLevel) {
        Optional<Title> optionalTitle = titleRepository.findById(currentLevel);
        if (optionalTitle.isPresent()) {
            user.setTitle(optionalTitle.get());
        }
    }
}
