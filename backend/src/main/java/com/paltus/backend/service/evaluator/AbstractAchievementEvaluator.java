package com.paltus.backend.service.evaluator;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.paltus.backend.event.AchievementUnlockedEvent;
import com.paltus.backend.model.Achievement;
import com.paltus.backend.model.User;
import com.paltus.backend.model.UserAchievement;
import com.paltus.backend.repository.AchievementRepository;
import com.paltus.backend.repository.UserAchievementRepository;

/**
 * Base class for evaluating and updating user progress
 * for achievements of a specific type.
 */
public abstract class AbstractAchievementEvaluator implements AchievementEvaluator {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    protected final ApplicationEventPublisher eventPublisher;

    public AbstractAchievementEvaluator(AchievementRepository achievementRepository,
            UserAchievementRepository userAchievementRepository, ApplicationEventPublisher eventPublisher) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Calculates and updates the user's progress on all achievements
     * of the current type based on the completed targets.
     * 
     * @param user               the user whose progress is being updated
     * @param currentTargetCount the current count of completed targets towards the
     *                           achievement goal
     */
    protected void calculateProgress(User user, int completedTargets) {
        List<Achievement> achievements = achievementRepository.findAllByType(getType());

        // calculate the progress for each incomplete achievement
        for (Achievement achievement : achievements) {
            UserAchievement userAchievement = userAchievementRepository.findByUserAndAchievement(user, achievement)
                    .orElse(new UserAchievement(user, achievement, 0));
            if (userAchievement.getProgress() < 100) {
                int currentProgress = Math.min(100,
                        (int) ((double) (completedTargets * 100) / achievement.getTargetCount()));
                userAchievement.setProgress(currentProgress);
                userAchievementRepository.save(userAchievement);
                if (currentProgress >= 100) {
                    eventPublisher.publishEvent(new AchievementUnlockedEvent(user.getId(), achievement));
                }
            }
        }
    }
}
