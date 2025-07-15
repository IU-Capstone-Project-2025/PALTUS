package com.paltus.backend.service.evaluator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.paltus.backend.model.User;
import com.paltus.backend.model.UserLogin;
import com.paltus.backend.model.enums.AchievementType;
import com.paltus.backend.repository.AchievementRepository;
import com.paltus.backend.repository.UserAchievementRepository;
import com.paltus.backend.repository.UserLoginRepository;

@Component
public class LoginStreakEvaluator extends AbstractAchievementEvaluator {
    private final UserLoginRepository userLoginRepository;

    public LoginStreakEvaluator(UserLoginRepository userLoginRepository, AchievementRepository achievementRepository,
            UserAchievementRepository userAchievementRepository, ApplicationEventPublisher eventPublisher) {
        super(achievementRepository, userAchievementRepository, eventPublisher);
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public void updateProgress(User user) {
        LocalDate today = LocalDate.now();

        if (!userLoginRepository.existsByEmailAndLoginDate(user.getEmail(), today)) {
            userLoginRepository.save(new UserLogin(user.getEmail(), today));
        }

        List<LocalDate> logins = userLoginRepository.findAllLoginDatesByUserEmailDesc(user.getEmail());

        if (logins.isEmpty())
            return;

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

        calculateProgress(user, streak);

    }

    @Override
    public AchievementType getType() {
        return AchievementType.LOGIN_STREAK;
    }
}
