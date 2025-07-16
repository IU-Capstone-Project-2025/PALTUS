package com.paltus.backend.service.evaluator;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.paltus.backend.event.ExpGotEvent;
import com.paltus.backend.model.User;
import com.paltus.backend.model.enums.AchievementType;
import com.paltus.backend.repository.AchievementRepository;
import com.paltus.backend.repository.UserAchievementRepository;
import com.paltus.backend.repository.UserRepository;

@Component
public class CompleteQuizesEvaluator extends AbstractAchievementEvaluator {
    private final UserRepository userRepository;

    private final int EXP_FOR_QUIZ = 25;

    public CompleteQuizesEvaluator(AchievementRepository achievementRepository,
            UserAchievementRepository userAchievementRepository, ApplicationEventPublisher eventPublisher,
            UserRepository userRepository) {
        super(achievementRepository, userAchievementRepository, eventPublisher);
        this.userRepository = userRepository;
    }

    @Override
    public void updateProgress(User user) {
        user.setFinishedQuizes(user.getFinishedQuizes() + 1);
        eventPublisher.publishEvent(new ExpGotEvent(user.getId(), EXP_FOR_QUIZ));
        userRepository.save(user);
        calculateProgress(user, user.getFinishedQuizes());
    }

    @Override
    public AchievementType getType() {
        return AchievementType.COMPLETE_QUIZES;
    }
}
