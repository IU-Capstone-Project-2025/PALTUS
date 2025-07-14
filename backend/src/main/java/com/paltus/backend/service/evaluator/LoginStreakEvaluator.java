package com.paltus.backend.service.evaluator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paltus.backend.model.Achievement;
import com.paltus.backend.model.User;
import com.paltus.backend.model.UserAchievement;
import com.paltus.backend.model.UserLogin;
import com.paltus.backend.model.enums.AchievementType;
import com.paltus.backend.repository.AchievementRepository;
import com.paltus.backend.repository.UserAchievementRepository;
import com.paltus.backend.repository.UserLoginRepository;

@Component
public class LoginStreakEvaluator implements AchievementEvaluator {
    private final UserLoginRepository userLoginRepository;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;

    @Autowired
    public LoginStreakEvaluator(UserLoginRepository userLoginRepository, AchievementRepository achievementRepository,
            UserAchievementRepository userAchievementRepository) {
        this.userLoginRepository = userLoginRepository;
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
    }

    @Override
    public void updateProgress(User user) {
        LocalDate today = LocalDate.now();

        if (!userLoginRepository.existsByEmailAndLoginDate(user.getEmail(), today)) {
            userLoginRepository.save(new UserLogin(user.getEmail(), today));
        }


        List<Achievement> achievements = achievementRepository.findAllByType(getType());
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

        for (Achievement achievement : achievements) {
            System.out.println(achievement.getName());
            UserAchievement userAchievement = userAchievementRepository.findByUserAndAchievement(user, achievement)
                    .orElse(new UserAchievement(user, achievement, 0));
            if (userAchievement.getProgress() < 100) {
                System.out.println(streak);
                userAchievement.setProgress(Math.min(100, (int)((double)(streak * 100) / achievement.getTargetCount())));
                userAchievementRepository.save(userAchievement);
            }
        }
    }

    @Override
    public AchievementType getType() {
        return AchievementType.LOGIN_STREAK;
    }
}
