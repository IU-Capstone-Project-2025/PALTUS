package com.paltus.backend.service.evaluator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.paltus.backend.model.enums.AchievementType;

/**
 * Factory that provides the appropriate AchievementEvaluator implementation
 * based on the AchievementType.
 */
@Component
public class AchievementEvaluatorFactory {
    private final Map<AchievementType, AchievementEvaluator> evaluators;

    public AchievementEvaluatorFactory(List<AchievementEvaluator> evaluatorList) {
        // Build a map from AchievementType to its corresponding evaluator
        this.evaluators = evaluatorList.stream()
        .collect(Collectors.toMap(AchievementEvaluator::getType, Function.identity()));
    }

    /**
     * Returns the evaluator associated with the given achievement type.
     *
     * @param type the type of achievement
     * @return the corresponding evaluator, or null if not found
     */
    public AchievementEvaluator getEvaluator(AchievementType type) {
        return evaluators.get(type);
    }
}
