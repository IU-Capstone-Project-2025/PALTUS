package com.paltus.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.paltus.backend.model.dto.AchievementWithProgressDto;
import com.paltus.backend.service.AchievementService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {
    private AchievementService achievementService;

    public UserController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }
    @GetMapping("/hello")
    public String getMethodName() {
        return "Hello test2";
    }

    @GetMapping("/achievements")
    public List<AchievementWithProgressDto> getAchievements() {
        return achievementService.getAchievementsWithProgress();
    }
}
