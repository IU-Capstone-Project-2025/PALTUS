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
public class CompleteQuizzesEvaluator extends AbstractAchievementEvaluator {
    private final UserRepository userRepository;

    private final int EXP_FOR_QUIZ = 25;

    public CompleteQuizzesEvaluator(AchievementRepository achievementRepository,
            UserAchievementRepository userAchievementRepository, ApplicationEventPublisher eventPublisher,
            UserRepository userRepository) {
        super(achievementRepository, userAchievementRepository, eventPublisher);
        this.userRepository = userRepository;
    }

    /**
     * Updates the user's quiz completion progress and emits an experience gain event.
     * Should be called when the user finishes a quiz.
     */
    @Override
    public void updateProgress(User user) {
        user.setFinishedQuizzes(user.getFinishedQuizzes() + 1);
        eventPublisher.publishEvent(new ExpGotEvent(user.getId(), EXP_FOR_QUIZ));
        userRepository.save(user);
        calculateProgress(user, user.getFinishedQuizzes());
    }

    @Override
    public AchievementType getType() {
        return AchievementType.COMPLETE_QUIZZES;
    }
}
