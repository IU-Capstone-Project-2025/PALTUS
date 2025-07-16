package com.paltus.backend.service.evaluator;

import com.paltus.backend.model.User;
import com.paltus.backend.model.enums.AchievementType;

public interface AchievementEvaluator {

    void updateProgress(User user);

    AchievementType getType();
}
