package com.paltus.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paltus.backend.model.Achievement;
import com.paltus.backend.model.User;
import com.paltus.backend.model.UserAchievement;
import com.paltus.backend.model.dto.AchievementWithProgressDto;
import com.paltus.backend.model.dto.UserGameStatsDto;
import com.paltus.backend.model.enums.AchievementType;
import com.paltus.backend.repository.AchievementRepository;
import com.paltus.backend.repository.UserAchievementRepository;
import com.paltus.backend.service.evaluator.AchievementEvaluator;
import com.paltus.backend.service.evaluator.AchievementEvaluatorFactory;

@Service
public class AchievementService {
    private final AchievementEvaluatorFactory achievementEvaluatorFactory;
    private final UserService userService;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;

    @Autowired
    public AchievementService(AchievementEvaluatorFactory achievementEvaluatorFactory, UserService userService,
            AchievementRepository achievementRepository, UserAchievementRepository userAchievementRepository) {
        this.achievementEvaluatorFactory = achievementEvaluatorFactory;
        this.userService = userService;
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
    }

    public void updateProgress(AchievementType type) {
        AchievementEvaluator evaluator = achievementEvaluatorFactory.getEvaluator(type);
        evaluator.updateProgress(userService.getCurrentUser());
    }

    /**
     * Returns all achievements along with the user's current progress on each.
     */
    public List<AchievementWithProgressDto> getAchievementsWithProgress() {
        List<AchievementWithProgressDto> achievemenstWithProgress = new ArrayList<>();
        User user = userService.getCurrentUser();

        for (AchievementType achievementType : AchievementType.values()) {
            List<Achievement> achievements = achievementRepository.findAllByType(achievementType);

            for (Achievement achievement : achievements) {
                // Get the user's progress for this achievement,
                // or initialize if none exists yet
                UserAchievement userAchievement = userAchievementRepository
                        .findByUserAndAchievement(user, achievement)
                        .orElse(new UserAchievement(user, achievement, 0));

                achievemenstWithProgress
                        .add(new AchievementWithProgressDto(achievement.getName(), achievement.getDescription(),
                                userAchievement.getProgress(), userAchievement.getProgress() >= 100));
            }
        }
        return achievemenstWithProgress;
    }

    /**
     * Returns the user's gamification-related stats including level, experience,
     * and achievements.
     */
    public UserGameStatsDto getUserGameStats() {
        User user = userService.getCurrentUser();
        return new UserGameStatsDto(userService.getStreak(), user.getLevel(), user.getCurrentExp(),
                user.getRequiredExp(), user.getTitle().getName(),
                getAchievementsWithProgress());
    }
}
