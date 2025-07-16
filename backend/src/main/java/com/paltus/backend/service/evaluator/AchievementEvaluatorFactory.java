package com.paltus.backend.service.evaluator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.paltus.backend.model.enums.AchievementType;

@Component
public class AchievementEvaluatorFactory {
    private final Map<AchievementType, AchievementEvaluator> evaluators;

    public AchievementEvaluatorFactory(List<AchievementEvaluator> evaluatorList) {
        this.evaluators = evaluatorList.stream()
        .collect(Collectors.toMap(AchievementEvaluator::getType, Function.identity()));
    }

    public AchievementEvaluator getEvaluator(AchievementType type) {
        return evaluators.get(type);
    }
}
