package com.paltus.backend.service.evaluator;

import com.paltus.backend.model.Achievement;
import com.paltus.backend.model.User;

public interface AchievementEvaluator {
    int evaluateProgress(User user, Achievement achievement);
}
