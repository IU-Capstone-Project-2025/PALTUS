package com.paltus.backend.service.evaluator;

import org.springframework.context.ApplicationEventPublisher;

import com.paltus.backend.model.User;
import com.paltus.backend.model.enums.AchievementType;
import com.paltus.backend.repository.AchievementRepository;
import com.paltus.backend.repository.UserAchievementRepository;
import com.paltus.backend.repository.UserRepository;

public class CompleteLessonsEvaluator extends AbstractAchievementEvaluator {
    private final UserRepository userRepository;

    public CompleteLessonsEvaluator(AchievementRepository achievementRepository,
            UserAchievementRepository userAchievementRepository, ApplicationEventPublisher eventPublisher,
            UserRepository userRepository) {
        super(achievementRepository, userAchievementRepository, eventPublisher);
        this.userRepository = userRepository;
    }

    @Override
    public void updateProgress(User user) {
        user.setFinishedLessons(user.getFinishedLessons() + 1);
        userRepository.save(user);
        calculateProgress(user, user.getFinishedLessons());
    }

    @Override
    public AchievementType getType() {
        return AchievementType.COMPLETE_LESSONS;
    }

}
