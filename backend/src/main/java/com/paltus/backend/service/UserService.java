package com.paltus.backend.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paltus.backend.model.User;
import com.paltus.backend.model.dto.UserLastActivityDto;
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

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // TODO: при отображении стрика не занулять его в бд, только визуально,
    // зануление только при следующем обновлении сабтопика

    public void updateStreak(long courseId) {
        UserLastActivityDto userLastActivityDto = userRepository.findUserIdAndLastActivityByCourseId(courseId);

        LocalDate lastActivityTime = userLastActivityDto.lastActivityTime();
        LocalDate currentDate = LocalDate.now();

        if (lastActivityTime == null) {
            userRepository.incrementStreak(userLastActivityDto.id(), currentDate);
            return;
        }

        long daysBetween = ChronoUnit.DAYS.between(lastActivityTime, currentDate);

        if (daysBetween > 1) {
            userRepository.resetStreak(userLastActivityDto.id());
        } else if (daysBetween == 1) {
            userRepository.incrementStreak(userLastActivityDto.id(), currentDate);
        }
    }
}
