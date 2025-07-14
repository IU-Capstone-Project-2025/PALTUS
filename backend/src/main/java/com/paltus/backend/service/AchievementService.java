package com.paltus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paltus.backend.model.enums.AchievementType;
import com.paltus.backend.service.evaluator.AchievementEvaluator;
import com.paltus.backend.service.evaluator.AchievementEvaluatorFactory;

@Service
public class AchievementService {
    private final AchievementEvaluatorFactory achievementEvaluatorFactory;
    private final UserService userService;

    @Autowired
    public AchievementService(AchievementEvaluatorFactory achievementEvaluatorFactory, UserService userService) {
        this.achievementEvaluatorFactory = achievementEvaluatorFactory;
        this.userService = userService;
    }

    public void updateProgress(AchievementType type) {
        AchievementEvaluator evaluator = achievementEvaluatorFactory.getEvaluator(type);
        evaluator.updateProgress(userService.getCurrentUser());
    }
}
