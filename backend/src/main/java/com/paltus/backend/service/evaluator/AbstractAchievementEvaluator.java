package com.paltus.backend.service.evaluator;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;

import com.paltus.backend.event.AchievementUnlockedEvent;
import com.paltus.backend.model.Achievement;
import com.paltus.backend.model.User;
import com.paltus.backend.model.UserAchievement;
import com.paltus.backend.repository.AchievementRepository;
import com.paltus.backend.repository.UserAchievementRepository;

public abstract class AbstractAchievementEvaluator implements AchievementEvaluator {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private  final ApplicationEventPublisher eventPublisher;

    public AbstractAchievementEvaluator(AchievementRepository achievementRepository,
            UserAchievementRepository userAchievementRepository, ApplicationEventPublisher eventPublisher) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.eventPublisher = eventPublisher;
    }

    protected void calculateProgress(User user, int currentTargetCount) {
        List<Achievement> achievements = achievementRepository.findAllByType(getType());

        for (Achievement achievement : achievements) {
            UserAchievement userAchievement = userAchievementRepository.findByUserAndAchievement(user, achievement)
                    .orElse(new UserAchievement(user, achievement, 0));
            if (userAchievement.getProgress() < 100) {
                int currentProgress = Math.min(100,
                        (int) ((double) (currentTargetCount * 100) / achievement.getTargetCount()));
                userAchievement.setProgress(currentProgress);
                userAchievementRepository.save(userAchievement);
                if (currentProgress >= 100) {
                    eventPublisher.publishEvent(new AchievementUnlockedEvent(user.getId(), achievement));
                }
            }
        }
    }
}
