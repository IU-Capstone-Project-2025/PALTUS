package com.paltus.backend.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paltus.backend.model.User;
import com.paltus.backend.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user;
    }

    public int getStreak() {
        User user = getCurrentUser();
        LocalDate lastActivityTime = user.getLastActivityTime();
        LocalDate currentDate = LocalDate.now();

        if (lastActivityTime == null || ChronoUnit.DAYS.between(lastActivityTime, currentDate) > 1) {
            return 0;
        }
        return user.getStreak();
    }

    public void updateStreak() {
        User user = getCurrentUser();

        LocalDate lastActivityTime = user.getLastActivityTime();
        LocalDate currentDate = LocalDate.now();

        if (lastActivityTime == null) {
            userRepository.incrementStreak(user.getId(), currentDate);
            return;
        }

        long daysBetween = ChronoUnit.DAYS.between(lastActivityTime, currentDate);

        if (daysBetween > 1) {
            userRepository.resetStreak(user.getId());
        } else if (daysBetween == 1) {
            userRepository.incrementStreak(user.getId(), currentDate);
        }
    }
}
