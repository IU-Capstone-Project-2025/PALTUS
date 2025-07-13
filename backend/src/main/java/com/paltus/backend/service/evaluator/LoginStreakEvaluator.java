package com.paltus.backend.service.evaluator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paltus.backend.model.Achievement;
import com.paltus.backend.model.User;
import com.paltus.backend.model.enums.AchievementType;
import com.paltus.backend.repository.UserLoginRepository;

@Service
public class LoginStreakEvaluator implements AchievementEvaluator {
    private final UserLoginRepository userLoginRepository;

    @Autowired
    public LoginStreakEvaluator(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public int evaluateProgress(User user, Achievement achievement) {
        List<LocalDate> logins = userLoginRepository.findAllLoginDatesByUserEmailDesc(user.getEmail());
        if (logins.isEmpty())
            return 0;

        int streak = 0;
        LocalDate expectedDate = LocalDate.now();

        for (LocalDate loginDate : logins) {
            if (loginDate.equals(expectedDate)) {
                streak++;
                expectedDate = expectedDate.minusDays(1);
            } else if (loginDate.isBefore(expectedDate)) {
                break;
            }
        }

        int target = achievement.getTargetCount();
        return Math.min(100, (streak * 100) / target);
    }

    @Override
    public AchievementType getType() {
        return AchievementType.LOGIN_STREAK;
    }
}
